package com.datals.foundation.interactor.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intersections.ibis.runtime.assertion.ContractAssert;

/**
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public abstract class AbstractTask implements Task {

	private static final Logger log = LoggerFactory.getLogger(AbstractTask.class);

	protected TaskContext taskContext;

	public void run() {
		log.debug("Start task execution : " + taskContext.getId());
		
		TaskResult taskResult = execute();
		
		log.debug("TaskResult  : " + taskResult);
	    taskContext.setTaskResult(taskResult);
	    
		log.debug("End task execution : " + taskContext.getId());
	}
	
	public abstract TaskResult execute();
	
	public TaskContext getTaskContext() {
		ContractAssert.postCondition(taskContext != null, "taskContext is null");
		return taskContext;
	}
	
	public void setTaskContext(TaskContext taskContext) {
		ContractAssert.preCondition(taskContext != null, "taskContext is null");
		this.taskContext = taskContext;
	}
	
}
