package com.datals.foundation.model.statemachine;

/**
 * 
 * @author dorel
 *
 */
public interface StateMachineController {

	boolean triggerEvent(StateEvent event, StateMachineContext context);
	
	void addStateChangeListener(StateChangeListener listener);

	void removeStateChangeListener(StateChangeListener listener);
}
