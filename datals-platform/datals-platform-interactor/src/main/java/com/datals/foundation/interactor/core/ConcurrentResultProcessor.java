package com.datals.foundation.interactor.core;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.intersections.ibis.interactor.api.client.ClientContext;
import com.intersections.ibis.interactor.core.InteractorConstants;
import com.intersections.ibis.interactor.core.processor.StatusChangeListener;
import com.intersections.ibis.interactor.core.processor.StatusMonitor;
import com.intersections.ibis.interactor.core.processor.TaskContextStore;
import com.intersections.ibis.interactor.core.processor.TaskThreadPoolExecutor;
import com.intersections.ibis.platform.LifeCycle;
import com.intersections.ibis.runtime.config.PropertiesBean;


/**
 * Listens for notifications about existing results to be consumed than delegates the consumption to a <code>ResultHandlerTask</code>.
 *  
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class ConcurrentResultProcessor implements Runnable, StatusChangeListener, LifeCycle {

	private static final Logger log = LoggerFactory.getLogger(ConcurrentResultProcessor.class);
	
	private Thread owner;
    private TaskThreadPoolExecutor executor;
    private int coreThreads;
	private int maxThreads;
	private long keepAlive;
    @Inject
    PropertiesBean propertiesBean;
    @Inject
    private ClientContextRegistry clientRegistry;
    @Inject
    private Provider<ResultHandler> resultHandlerProvider;
    @Inject
    private StatusMonitor taskStatusMonitor;
    @Inject
    private TaskContextStore taskContextStore;
    
    boolean processingStopped = false;
    
    @Override
	public void init() {
		owner = new Thread(this, this.getClass().getName());
        owner.start();
        taskContextStore.addStatusChangeListener(this);
        coreThreads = propertiesBean.getIntegerOrDefault(InteractorConstants.RESULT_CORE_THREADS, 10);
        maxThreads = propertiesBean.getIntegerOrDefault(InteractorConstants.RESULT_MAX_THREADS, 100);
        keepAlive = propertiesBean.getIntegerOrDefault(InteractorConstants.RESULT_KEEPALIVE_THREADS, 300);
        executor = new TaskThreadPoolExecutor(coreThreads, maxThreads,
                keepAlive, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
        log.debug("ConcurrentResultProcessor initialized.");
	}
	
    @Override
	public void notifyStatusChanged() {
    	synchronized (taskStatusMonitor) {
        	taskStatusMonitor.incrementUpdateCounter();
        	taskStatusMonitor.notify();
        }
	}
   	
    @Override
	public void run() {
		while (!processingStopped) {
			try {
				synchronized (taskStatusMonitor) {
					if (taskStatusMonitor.getUpdateCounter() == 0) {
						taskStatusMonitor.wait();
					}
					taskStatusMonitor.resetUpdateCounter();
				}
				Set<String> registeredClientIds = clientRegistry.getRegisteredClientIds();
				for (String clientId : registeredClientIds) {
					if (taskContextStore.isUpdatedClient(clientId)) {
						ClientContext clientContext = clientRegistry.unregisterClientContext(clientId);
						if (clientContext != null && clientContext.isValid()) {
							ResultHandler resultHandler = resultHandlerProvider.get();
							resultHandler.setClientId(clientId);
							resultHandler.setClientContext(clientContext);
							executor.execute(resultHandler);
						}
					}
				}
			} catch (InterruptedException e) {
				log.error("ConcurrentResultProcessor interrupted", e);
			}
		}
	}
    
	@Override
	public void destroy() {
		executor.shutdownNow();
		processingStopped = true;
		if (owner != null) {
			owner.interrupt();
		}
		log.debug("ConcurrentResultProcessor destroyed.");
	}

}
