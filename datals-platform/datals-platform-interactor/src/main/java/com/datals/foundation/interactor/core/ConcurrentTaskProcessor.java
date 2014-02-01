package com.datals.foundation.interactor.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datals.foundation.interactor.api.Task;
import com.datals.foundation.interactor.api.TaskContext;
import com.google.inject.Inject;
import com.intersections.ibis.interactor.core.InteractorConstants;
import com.intersections.ibis.interactor.core.processor.TaskThreadPoolExecutor;
import com.intersections.ibis.runtime.config.PropertiesBean;

/**
 * Processes a task using a <code>ThreadPoolExecutor</code>.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class ConcurrentTaskProcessor implements TaskProcessor {

	private static final Logger log = LoggerFactory.getLogger(ConcurrentTaskProcessor.class);
	
	private int coreThreads;
	private int maxThreads;
	private long keepAlive;
	@Inject
	private PropertiesBean propertiesBean;
	protected TaskThreadPoolExecutor executor;
	
	@Override
	public void init() {
		coreThreads = propertiesBean.getIntegerOrDefault(InteractorConstants.TASK_CORE_THREADS, 10);
		maxThreads = propertiesBean.getIntegerOrDefault(InteractorConstants.TASK_MAX_THREADS, 100);
		keepAlive = propertiesBean.getIntegerOrDefault(InteractorConstants.TASK_KEEPALIVE_THREADS, 300);
		executor = new TaskThreadPoolExecutor(coreThreads, maxThreads, 
				keepAlive, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

		log.debug("ThreadPoolTaskProcessor initialized.");
	}
	
	@Override
	public void processTask(Task task) {
        executor.execute(task);
        log.debug("Task scheduled for execution : " + task.getTaskContext());
	}

	@Override
    public TaskContext getCurrentContext() {
        return executor.getCurrentContext();
    }
	
	@Override
	public void destroy() {
		executor.shutdownNow();
		log.debug("ThreadPoolTaskProcessor destroyed.");
	}

}
