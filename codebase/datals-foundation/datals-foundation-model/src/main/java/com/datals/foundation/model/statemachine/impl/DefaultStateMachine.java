package com.datals.foundation.model.statemachine.impl;

import com.datals.foundation.model.statemachine.api.State;
import com.datals.foundation.model.statemachine.api.StateChangeListener;
import com.datals.foundation.model.statemachine.api.StateEvent;
import com.datals.foundation.model.statemachine.api.StateMachine;

/**
 * 
 * @author dorel
 *
 */
public class DefaultStateMachine implements StateMachine {
	
	private StateMachineContext context;
	private StateMachineController controller;
	
	@Override
	public State getCurrentState() {
		return context.getCurrentState();
	}

	@Override
	public boolean triggerEvent(StateEvent stateEvent) {
		return controller.triggerEvent(stateEvent, context);
	}

	@Override
	public void addStateChangeListener(StateChangeListener listener) {
		controller.addStateChangeListener(listener);
	}

	@Override
	public void removeStateChangeListener(StateChangeListener listener) {
		controller.removeStateChangeListener(listener);
	}

	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deactivate() {
		// TODO Auto-generated method stub
		return false;
	}

}
