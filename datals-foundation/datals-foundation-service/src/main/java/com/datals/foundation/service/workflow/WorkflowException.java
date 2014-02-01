package com.datals.foundation.service.workflow;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class WorkflowException extends Exception {

	private static final long serialVersionUID = 5441179566994398150L;

	public WorkflowException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkflowException(String message) {
		super(message);
	}

	public WorkflowException(Throwable cause) {
		super(cause);
	}

}
