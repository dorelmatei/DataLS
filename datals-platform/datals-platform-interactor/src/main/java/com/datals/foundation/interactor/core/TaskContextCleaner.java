package com.datals.foundation.interactor.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.intersections.ibis.interactor.core.ServiceInteractor;


/**
 * Once in a while removes <code>TaskContext</code> instances from the <code>TaskContextStore</code> 
 * that were not consumed within a certain period of time.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class TaskContextCleaner implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(ServiceInteractor.class);

    private static final long OBSOLETE_TASKS_TIMEOUT = 1000L * 60 * 1;
    private static final long SLEEP_TIMEOUT = 1000L * 60 * 1;
    
    private boolean processingStopped = false;
    private Thread ownerThread;
    
    @Inject
    private TaskContextStore taskContextStore;

    @Override
    public void run() {
        ownerThread = Thread.currentThread();
        while (!processingStopped) {
        	try {
        		taskContextStore.removeObsoleteTasks(OBSOLETE_TASKS_TIMEOUT);
                Thread.sleep(SLEEP_TIMEOUT);
            } catch (InterruptedException e) {
            	log.error(e.getMessage());
            } 
        }

    }

    public synchronized void setStopped(boolean stopped) {
        this.processingStopped = stopped;
        try {
            this.ownerThread.interrupt();
        } catch (Throwable t) {
        	log.error(t.getMessage());
        }

    }
}
