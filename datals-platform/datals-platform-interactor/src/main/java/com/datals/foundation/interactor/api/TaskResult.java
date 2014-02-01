package com.datals.foundation.interactor.api;

import com.intersections.ibis.platform.Result;
import com.intersections.ibis.runtime.assertion.ContractAssert;

/**
 * Result of a <code>Task</code> execution.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class TaskResult implements Result {

	private boolean successful;
	private String error;
	private String resultLocation;
	
	public TaskResult(boolean successful) {
		this.successful = successful;
	}
	
	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessfull(boolean successful) {
		this.successful = successful;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		ContractAssert.preCondition(error != null && !error.isEmpty(), "error is null or empty");
		ContractAssert.preCondition(!successful, "Cannot add error on a successful taskResult");
		this.error = error;
	}

	public Object getResultLocation() {
		return resultLocation;
	}

	public void setResultLocation(String resultLocation) {
		ContractAssert.preCondition(resultLocation != null && !resultLocation.isEmpty(), "resultLocation is null or empty");
		ContractAssert.preCondition(successful, "Cannot add resultLocation on a failed taskResult");
		this.resultLocation = resultLocation;
	}
	
	public String toString() {
		return successful ? "success:" + resultLocation : "fail:" + error;
	}
	
}
