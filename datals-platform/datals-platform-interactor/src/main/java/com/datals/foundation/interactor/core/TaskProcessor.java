package com.datals.foundation.interactor.core;

import com.datals.foundation.interactor.api.Task;
import com.datals.foundation.interactor.api.TaskContext;
import com.intersections.ibis.platform.LifeCycle;

/**
 * Processes a <code>Task</code> based on its contained <code>TaskContext</code>.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public interface TaskProcessor extends LifeCycle {

	void processTask(Task task);
	
	TaskContext getCurrentContext(); 
}
