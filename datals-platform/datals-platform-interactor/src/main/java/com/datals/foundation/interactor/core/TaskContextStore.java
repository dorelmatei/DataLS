package com.datals.foundation.interactor.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datals.foundation.interactor.api.TaskContext;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.intersections.ibis.platform.LifeCycle;
import com.intersections.ibis.runtime.assertion.ContractAssert;

/**
 * In memory store for all <code>TaskContext</code> instances resulted from a service report ready to be consumed by the clients.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
@Singleton
public class TaskContextStore implements LifeCycle {
	
	private static final Logger log = LoggerFactory.getLogger(TaskContextStore.class);
	
	private List<StatusChangeListener> statusChangeListeners = new ArrayList<StatusChangeListener>();
	private ConcurrentHashMap<String, LinkedBlockingQueue<TaskContext>> taskContextClientQueues = new ConcurrentHashMap<String, LinkedBlockingQueue<TaskContext>>();
	private ConcurrentHashMap<String, TaskContext> runningTasks = new ConcurrentHashMap<String, TaskContext>();
	
	@Inject
	private TaskContextCleaner cleanerTask;
	
	@Override
	public void init() {
		Thread reaperThread = new Thread(cleanerTask, "CleanerTask");
		reaperThread.setDaemon(true);
		reaperThread.start();
	}
	
	public TaskContext getTaskContext(String taskId) {
		return runningTasks.get(taskId);
	}
	
	public void addTaskContext(TaskContext taskContext) {
		ContractAssert.preCondition(taskContext != null, "taskContext is null");
		runningTasks.putIfAbsent(taskContext.getId(), taskContext);
	}
	
	public TaskContext takeTaskContext(String clientId, int waitTimeout)
			throws InterruptedException {
		ContractAssert.preCondition(clientId != null, "clientId is null");
		LinkedBlockingQueue<TaskContext> taskContextQueue = getClientQueue(clientId);
		if (taskContextQueue == null) {
			return null;
		}
		TaskContext taskContext = null;
		if (waitTimeout == -1) {
			taskContext = taskContextQueue.take();
		} else if (waitTimeout == 0) {
			taskContext = taskContextQueue.poll();
		} else {
			taskContext = taskContextQueue.poll(waitTimeout, TimeUnit.MILLISECONDS);
		}
		return taskContext;
	}

	public void enqueueTaskContext(TaskContext taskContext) {
		ContractAssert.preCondition(taskContext != null, "taskContext is null");
		LinkedBlockingQueue<TaskContext> clientQueue = getClientQueue(taskContext.getClientId());
		if (clientQueue != null) {
			try {
				clientQueue.put(taskContext);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
			for (StatusChangeListener listener : statusChangeListeners) {
				listener.notifyStatusChanged();
			}
			ContractAssert.postCondition(clientQueue.contains(taskContext));
		}
	}
	
	public void removeObsoleteTasks(long timeInterval) {
		ContractAssert.preCondition(timeInterval > 0, "timeInterval is negative or zero");
		Iterator<Map.Entry<String, TaskContext>> it = runningTasks.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, TaskContext> entry = it.next();
			TaskContext taskContext = (TaskContext) entry.getValue();
			if (taskContext.getTaskStatus().isTerminal()) {
				Date lastUpdate = taskContext.getTaskStatus().getUpdateDate();
				if ((lastUpdate != null)
						&& ((System.currentTimeMillis() - lastUpdate.getTime()) > timeInterval)) {
					it.remove();
				}
			}
		}

	}

	public void clearTaskContext(TaskContext taskContext) {
		ContractAssert.preCondition(taskContext != null, "taskContext is null");
		List<TaskContext> list = new ArrayList<TaskContext>(1);
		list.add(taskContext);
		LinkedBlockingQueue<TaskContext> updateClientQueue = getClientQueue(taskContext
				.getClientId());
		updateClientQueue.removeAll(list);
		runningTasks.remove(taskContext.getId());
	}
	
	public void addStatusChangeListener(StatusChangeListener listener) {
		this.statusChangeListeners.add(listener);
	}

	public void removeStatusChangeListener(StatusChangeListener listener) {
		this.statusChangeListeners.remove(listener);
	}

	public boolean isUpdatedClient(String clientId) {
		return getClientQueue(clientId).peek() != null;
	}
	
	private LinkedBlockingQueue<TaskContext> getClientQueue(String clientId) {
		LinkedBlockingQueue<TaskContext> queue = taskContextClientQueues.get(clientId);
		if (queue == null) {
			LinkedBlockingQueue<TaskContext> newQueue = new LinkedBlockingQueue<TaskContext>();
			queue = taskContextClientQueues.putIfAbsent(clientId, newQueue);
			if (queue == null) {
				queue = newQueue;
			}
		}
		ContractAssert.postCondition(queue != null, "queue is null");
		return queue;
	}

	@Override
	public void destroy() {
		if (cleanerTask != null) {
			cleanerTask.setStopped(true);
		}
	}

}
