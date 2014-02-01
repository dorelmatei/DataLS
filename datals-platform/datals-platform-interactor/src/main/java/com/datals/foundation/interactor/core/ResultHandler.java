package com.datals.foundation.interactor.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datals.foundation.interactor.api.TaskContext;
import com.datals.foundation.interactor.api.TaskStatus;
import com.google.inject.Inject;
import com.intersections.ibis.interactor.api.client.ClientContext;
import com.intersections.ibis.interactor.api.client.ClientResultConsumer;
import com.intersections.ibis.interactor.core.InteractorConstants;
import com.intersections.ibis.interactor.core.processor.TaskContextStore;
import com.intersections.ibis.runtime.assertion.ContractAssert;
import com.intersections.ibis.runtime.config.PropertiesBean;

/**
 * Extracts a <code>TaskContext</code> from <code>TaskContextStore</code> and dispatch it for consumption.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class ResultHandler implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(ResultHandler.class);
    
    private int retryTimes;
    private String clientId;
    private ClientContext clientContext;
    PropertiesBean propertiesBean;
    @Inject
    private TaskContextStore taskContextStore;
    @Inject
    private ClientContextRegistry clientRegistry;
    
    @Inject
    public ResultHandler(PropertiesBean propertiesBean) {
    	this.propertiesBean = propertiesBean;
    	this.retryTimes = propertiesBean.getIntegerOrDefault(InteractorConstants.RESULT_HANDLER_RETRY, 3);
    }

    public ResultHandler(String clientId, ClientContext clientContext) {
        this.clientId = clientId;
        this.clientContext = clientContext;
    }
    
    public void setClientId(String clientId) {
    	this.clientId = clientId;
    }
    
    public void setClientContext(ClientContext clientContext) {
    	this.clientContext = clientContext;
    }

    public void run() {
    	ContractAssert.preCondition(clientId != null, "clientId is null");
    	ContractAssert.preCondition(clientContext != null, "clientContext is null");
        TaskContext taskContext = null;
        try {
            taskContext = taskContextStore.takeTaskContext(clientId, 0);
                      
                if (taskContext != null) {
                	log.debug("TaskContext to handle : " + taskContext);  
                    TaskStatus status = taskContext.getTaskStatus();
                    synchronized (status) {
                        
                    	taskContextStore.clearTaskContext(taskContext);

                        ClientResultConsumer resultConsumer = clientRegistry.getClientContext(status.getClientId()).getResultConsumer();
                        if (resultConsumer == null) {
                            log.error("No ResultConsumer found for task: " + taskContext.getId());
                            return;
                        }
                        boolean consumed = resultConsumer.consume(clientContext, taskContext.getTaskResult());
                        if (consumed) {
                            log.debug("Result consumed for taskId: " + status.getTaskId() + " and clientId: " + this.clientId);
                        } else {
                            taskContext.getTaskStatus().setRetryCount(taskContext.getTaskStatus().getRetryCount()+1);
                            log.debug("Response not handled for taskId:" + status.getTaskId() + " and clientId: " + this.clientId + " Retrying...");
                            
                            if (taskContext.getTaskStatus().getRetryCount() <= retryTimes) {
                            	taskContextStore.enqueueTaskContext(taskContext);
                                log.debug("Retry Number: " + taskContext.getTaskStatus().getRetryCount() + "for TaskId: " + status.getTaskId()+ " and clientId: " + this.clientId);
                            } else {
                                log.debug("Retry number exceeded. Task: " + status.getTaskId() + " discarded.");
                            }
                        }

                    }
                    log.debug("TaskContext handled : " + taskContext);
                } 
        } catch (InterruptedException e) {
           log.error(e.getMessage());
        } 
    }

}
