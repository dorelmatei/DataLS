package com.datals.foundation.service.workflow.result;

import java.util.Map;

import com.google.inject.Inject;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class ResultDispatcherFactory {

	@Inject
	private Map<String, ResultDispatcher> resultDispatchers;
	
	public ResultDispatcher getResultDispatcher(String resultDispatcherType) {
		return resultDispatchers.get(resultDispatcherType);
	}
}
