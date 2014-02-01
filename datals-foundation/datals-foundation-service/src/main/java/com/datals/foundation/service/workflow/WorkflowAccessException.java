package com.datals.foundation.service.workflow;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class WorkflowAccessException extends Exception {

	private static final long serialVersionUID = 9048157278859286557L;

	public WorkflowAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkflowAccessException(String message) {
		super(message);
	}

	public WorkflowAccessException(Throwable cause) {
		super(cause);
	}

}
