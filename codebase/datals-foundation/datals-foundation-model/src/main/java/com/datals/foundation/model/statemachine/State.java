package com.datals.foundation.model.statemachine;

import java.util.Map;
import java.util.Set;

/**
 * 
 * @author dorel
 *
 */
public interface State extends Identifiable {

	Action getEntryAction();
	
	Action getExitAction();
	
	Action getDoAction();
	
	Map<String, Object> getPayload();
	
	boolean isFinal();
	
	Set<State> getSubStates();
}
