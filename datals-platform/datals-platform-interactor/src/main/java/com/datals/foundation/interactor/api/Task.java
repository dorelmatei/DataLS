package com.datals.foundation.interactor.api;


/**
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public interface Task extends Runnable {

     TaskResult execute();
     
     TaskContext getTaskContext();
	
}
