package com.datals.foundation.model.statemachine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
