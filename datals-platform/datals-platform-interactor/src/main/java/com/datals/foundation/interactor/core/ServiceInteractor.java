package com.datals.foundation.interactor.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datals.foundation.interactor.api.Interactor;
import com.datals.foundation.interactor.api.Task;
import com.datals.foundation.interactor.api.TaskContext;
import com.datals.foundation.interactor.api.TaskStatus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.intersections.ibis.interactor.api.client.ClientContext;
import com.intersections.ibis.runtime.assertion.ContractAssert;

/**
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
@Singleton
public class ServiceInteractor implements Interactor {

	private static final Logger log = LoggerFactory.getLogger(ServiceInteractor.class);
	
	@Inject
	private ConcurrentTaskProcessor taskProcessor;
	
	@Inject
	private ConcurrentResultProcessor resultProcessor;
	
	@Inject
	private TaskContextStore taskContextStore;
	
	@Inject
	ClientContextRegistry clientRegistry;

	@Override
	public void init() {
		taskContextStore.init();
		resultProcessor.init();
		taskProcessor.init();
		log.debug("InteractionController initialized.");
	}
	
	public void registerClient(ClientContext clientContext) {
		ContractAssert.preCondition(clientContext != null, "clientContext is null");
		clientRegistry.registerClientContext(clientContext);
	}
	
	public void unregisterClient(String clientId) {
		ContractAssert.preCondition(clientId != null, "clientId is null");
		clientRegistry.unregisterClientContext(clientId);
	}
	
	public boolean isRegisteredClient(String clientId) {
		ContractAssert.preCondition(clientId != null, "clientId is null");
		return clientRegistry.isRegisteredClient(clientId);
	}

	public void submitTask(Task task) {
		ContractAssert.preCondition(task != null, "task is null");
		ContractAssert.preCondition(task.getTaskContext() != null, "taskContext is null");
		taskContextStore.addTaskContext(task.getTaskContext());
		taskProcessor.processTask(task);
	}

	public void cancelTask(String taskId) {
		ContractAssert.preCondition(taskId != null, "taskId is null");
        TaskContext taskContext = taskContextStore.getTaskContext(taskId);
        if (taskContext != null) {
            TaskStatus taskStatus = taskContext.getTaskStatus();
            synchronized (taskStatus) {
                if (!taskStatus.isTerminal()) {
                    taskContext.setCancelRequested(TaskContext.CANCEL_REQUESTED);
                } 
            }
        } 
	}
	
	public TaskContext getCurrentTaskContext() {
		return taskProcessor.getCurrentContext();
	}

	@Override
	public void destroy() {
		taskProcessor.destroy();
		resultProcessor.destroy();
		taskContextStore.destroy();
		clientRegistry.clear();
        log.debug("InteractionController destroyed.");
	}

}
