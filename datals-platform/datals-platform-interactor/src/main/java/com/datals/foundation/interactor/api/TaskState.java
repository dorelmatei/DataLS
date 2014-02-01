package com.datals.foundation.interactor.api;

/**
 * States allowed during the task life cycle.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public enum TaskState {
    NEW, 
    SCHEDULED, 
    RUNNING, 
    CANCELING, 
    CANCELED, 
    COMPLETE, 
    ERROR;
}
