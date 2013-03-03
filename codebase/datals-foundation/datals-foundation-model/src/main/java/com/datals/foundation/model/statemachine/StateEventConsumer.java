package com.datals.foundation.model.statemachine;

import com.datals.foundation.model.base.Consumer;

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
