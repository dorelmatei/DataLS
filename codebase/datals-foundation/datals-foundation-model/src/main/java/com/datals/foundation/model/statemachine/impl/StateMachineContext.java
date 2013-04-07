package com.datals.foundation.model.statemachine.impl;

import com.datals.foundation.model.statemachine.api.State;

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
