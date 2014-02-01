package com.datals.foundation.interactor.core;

import com.google.inject.Singleton;


/**
 * Monitor object used to signal the occurrence of a new update on some of the <code>TaskContext</code> instances.
 *
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
@Singleton
public class StatusMonitor {

    private long updateCounter = 0;

    public void incrementUpdateCounter() {
    	updateCounter++;
    }

    public void resetUpdateCounter() {
    	updateCounter = 0L;
    }

    public long getUpdateCounter() {
        return updateCounter;
    }
    
    public String toString() {
    	return "monitor:" + updateCounter;
    }
}
