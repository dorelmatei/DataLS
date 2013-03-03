package com.datals.foundation.model.statemachine;

/**
 * 
 * @author dorel
 *
 */
public interface StateMachineContext {

	State getCurrentState();
	
	Object getAttribute(String key);
	
	void setAttribute(String key, Object attribute);
}
