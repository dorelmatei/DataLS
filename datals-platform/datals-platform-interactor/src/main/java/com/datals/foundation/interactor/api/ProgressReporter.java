package com.datals.foundation.interactor.api;


/**
 * Reports the progress made during <code>Task</code> execution.
 *  
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public interface ProgressReporter {

	void reportBegin(int workUnits);

	void reportWork(int workUnits, int workDone);

	void reportComplete(int workUnits, int workDone, String resultLocation);
	
	void reportCancel(int workUnits, int workDone);

	void reportError(int workUnits, int workDone, String errorMessage);
}
