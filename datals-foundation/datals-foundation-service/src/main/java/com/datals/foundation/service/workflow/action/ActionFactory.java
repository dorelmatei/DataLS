package com.datals.foundation.service.workflow.action;

import java.util.Map;

import com.google.inject.Inject;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class ActionFactory {

	@Inject
	private Map<String, Action> actions;
	
	public Action getAction(String actionHandlerType) {
		
		return actions.get(actionHandlerType);
	}
}
