package com.datals.foundation.model.statemachine;

/**
 * 
 * @author dorel
 *
 */
public interface Action {

	void execute(StateMachineContext stateMachineContext);
}
