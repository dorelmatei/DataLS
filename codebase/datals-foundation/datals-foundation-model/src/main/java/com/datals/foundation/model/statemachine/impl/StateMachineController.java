package com.datals.foundation.model.statemachine.impl;

import com.datals.foundation.model.statemachine.api.StateChangeListener;
import com.datals.foundation.model.statemachine.api.StateEvent;

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
