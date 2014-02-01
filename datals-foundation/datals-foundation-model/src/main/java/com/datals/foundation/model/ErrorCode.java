package com.datals.foundation.model;

/**
 * 
 * @author <a href="mailto:dorelmatei@yahoo.com">Dorel Matei</a>
 */
public class ErrorCode<T> {
	
	private T code;
	private String description;
	
	public ErrorCode(T code, String description) {
		this.code = code;
		this.description = description;
	}

	public T getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
}
