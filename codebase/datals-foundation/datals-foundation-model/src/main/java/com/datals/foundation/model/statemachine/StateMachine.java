package com.datals.foundation.model.statemachine;

import com.datals.foundation.model.base.Manageable;

/**
 * 
 * @author dorel
 *
 */
public interface StateMachine extends Identifiable, Manageable{
	
	State getCurrentState();
	
	boolean triggerEvent(StateEvent stateEvent);
	
	void addStateChangeListener(StateChangeListener listener);
	
	void removeStateChangeListener(StateChangeListener listener);
}
