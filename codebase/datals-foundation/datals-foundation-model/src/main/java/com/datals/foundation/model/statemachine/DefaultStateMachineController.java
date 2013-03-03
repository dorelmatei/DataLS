package com.datals.foundation.model.statemachine;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author dorel
 *
 */
public class DefaultStateMachineController implements StateMachineController {
	
	private Set<StateChangeListener> stateChangeListeners = new HashSet<StateChangeListener>();

	@Override
	public boolean triggerEvent(StateEvent event, StateMachineContext context) {
		
		return false;
	}

	@Override
	public void addStateChangeListener(StateChangeListener listener) {
		stateChangeListeners.add(listener);
	}

	@Override
	public void removeStateChangeListener(StateChangeListener listener) {
		stateChangeListeners.remove(listener);
	}

}
