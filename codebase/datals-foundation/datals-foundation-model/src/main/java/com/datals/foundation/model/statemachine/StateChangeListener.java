package com.datals.foundation.model.statemachine;

/**
 * 
 * @author dorel
 *
 */
public interface StateChangeListener {

	void onChangeState(State currentState);
}
