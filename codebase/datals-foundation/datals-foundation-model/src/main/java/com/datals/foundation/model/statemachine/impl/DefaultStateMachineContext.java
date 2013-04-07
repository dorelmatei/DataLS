package com.datals.foundation.model.statemachine.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.datals.foundation.model.statemachine.api.State;

public class DefaultStateMachineContext implements StateMachineContext {
	
	private State currentState;
	private Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

	@Override
	public State getCurrentState() {
		return currentState;
	}

	@Override
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public void setAttribute(String key, Object attribute) {
		attributes.put(key, attribute);
	}

}
