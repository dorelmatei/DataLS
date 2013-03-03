package com.datals.foundation.model.base;

public interface Registry<T> {

	void register(String name, T t);
	
	T lookup(String name);
	
	void unregister(String name);
	
	void clear();
}
