package com.datals.foundation.interactor.api;

import java.util.concurrent.atomic.AtomicBoolean;

import com.intersections.ibis.platform.Identifiable;
import com.intersections.ibis.runtime.assertion.ContractAssert;

/**
 * Keeps input, output and identification data related to a <code>Task</code> execution.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class TaskContext implements Identifiable {

	public static AtomicBoolean CANCEL_NOT_REQUESTED = new AtomicBoolean(false);
	public static AtomicBoolean CANCEL_REQUESTED = new AtomicBoolean(true);
	
    private String id;
    private String clientId;
    private AtomicBoolean cancelRequested;
    private TaskStatus taskStatus;
    private TaskResult taskResult;
    private Thread executingThread;

    public TaskContext(String clientId, String taskId) {
        this.clientId = clientId;
        this.id = taskId;
        this.taskStatus = new TaskStatus(clientId, taskId);
    }
    
    @Override
	public String getId() {
		return id;
	}
   
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public String getClientId() {
        return clientId;
    }

    public void setExecutingThread(Thread t) {
        this.executingThread = t;
    }

    public Thread getExecutingThread() {
        return executingThread;
    }

	public AtomicBoolean isCancelRequested() {
		return cancelRequested;
	}

	public void setCancelRequested(AtomicBoolean cancelRequested) {
		this.cancelRequested = cancelRequested;
		taskStatus.setTaskState(TaskState.CANCELING);
	}

	public TaskResult getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(TaskResult taskResult) {
		ContractAssert.preCondition(taskResult != null, "taskResult is null");
		this.taskResult = taskResult;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		ContractAssert.preCondition(taskStatus != null, "taskStatus is null");
		this.taskStatus = taskStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TaskContext other = (TaskContext) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "id:" + id + ",clientId:" + clientId;
	}

}
