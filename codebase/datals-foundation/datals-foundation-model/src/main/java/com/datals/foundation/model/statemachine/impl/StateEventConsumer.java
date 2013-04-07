package com.datals.foundation.model.statemachine.impl;

import com.datals.foundation.model.base.Consumer;
import com.datals.foundation.model.statemachine.api.StateEvent;
import com.datals.foundation.model.statemachine.api.StateMachine;

/**
 * 
 * @author dorel
 *
 */
public class StateEventConsumer implements Consumer<StateEvent> {
	
	private StateMachine stateMachine;

	@Override
	public void consume(StateEvent stateEvent) {
		if (stateEvent != null) {
			boolean accepted = stateMachine.triggerEvent(stateEvent);
		}
	}

}
