package com.datals.foundation.model.statemachine;

/**
 * 
 * @author dorel
 *
 */
public interface Transition {

	State getSourceState();
	
	State getTargetState();
	
	Guard getGuard();
	
	Action getDoAction();
}
