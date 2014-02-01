package com.datals.foundation.interactor.api;

import java.util.Date;

import com.intersections.ibis.runtime.assertion.ContractAssert;

/**
 * Execution status of a <code>Task</code>.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class TaskStatus {

    private String clientId;
    private String taskId;
    private Date creationDate;
    private Date updateDate;
    private Date cancelDate;
    private int workUnits;
    private int workDone;
    private int retryCount;
    private TaskState taskState;

    public TaskStatus(String clientId, String taskId) {
        this.clientId = clientId;
        this.taskId = taskId;
        taskState = TaskState.NEW;
        creationDate = new Date();
    }
    
    public boolean isTerminal() {
        return (TaskState.CANCELED == taskState || 
        		TaskState.COMPLETE == taskState || 
        		TaskState.ERROR == taskState);
    }

    public String getClientId() {
        return clientId;
    }

    public String getTaskId() {
        return taskId;
    }

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date lastUpdate) {
		ContractAssert.preCondition(lastUpdate != null, "lastUpdate is null");
		ContractAssert.preCondition(lastUpdate.after(creationDate), "lastUpdate is not after creationDate");
		this.updateDate = lastUpdate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		ContractAssert.preCondition(cancelDate != null, "cancelDate is null");
		ContractAssert.preCondition(cancelDate.after(creationDate), "cancelDate is not after creationDate");
		this.cancelDate = cancelDate;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
		if (TaskState.COMPLETE == taskState) {
			workDone = workUnits;
		} else if (TaskState.CANCELING == taskState) {
			cancelDate = new Date();
		}
		updateDate = new Date();
	}

	public int getWorkUnits() {
		return workUnits;
	}

	public void setWorkUnits(int workUnits) {
		ContractAssert.preCondition(workUnits >= 0, "workUnits cannot be negative");
		this.workUnits = workUnits;
	}

	public int getWorkDone() {
		return workDone;
	}

	public void setWorkDone(int workDone) {
		ContractAssert.preCondition(workDone >= 0, "workDone cannot be negative");
		ContractAssert.preCondition(workDone <= workUnits, "workDone cannot be greater than workUnits");
		this.workDone = workDone;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		ContractAssert.preCondition(retryCount >= 0, "retryCount cannot be negative");
		this.retryCount = retryCount;
	}
	
	public String toString() {
		return taskState.toString();
	}
	
}
