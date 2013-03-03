package com.datals.foundation.model.base;

public interface Context {

	void setAttribute(String key, Object value);
	
	Object getAttribute(String key);
	
	void clear();
}
