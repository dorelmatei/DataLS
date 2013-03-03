package com.datals.foundation.model.base;

public interface Result<T> {

	boolean isSuccessfull();
	
	T getPayload();
	
}
