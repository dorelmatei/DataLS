package com.datals.foundation.service.workflow;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.intersections.ibis.common.platform.services.workflow.action.Action;
import com.intersections.ibis.common.platform.services.workflow.action.ActionFactory;
import com.intersections.ibis.common.platform.services.workflow.bind.ActionHandler;
import com.intersections.ibis.common.platform.services.workflow.bind.WorkflowPhase;
import com.intersections.ibis.common.platform.services.workflow.result.ResultDispatcher;
import com.intersections.ibis.common.platform.services.workflow.result.ResultDispatcherFactory;
import com.intersections.ibis.common.runtime.assertion.ContractAssert;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class DefaultWorkflowProcessor implements WorkflowProcessor {

	private static final Logger log = LoggerFactory.getLogger(DefaultWorkflowProcessor.class);
	
	@Inject 
	private ActionFactory actionFactory;
	@Inject
	private ResultDispatcherFactory resultDispatcherFactory;
	
	
	@Override
	public void processWorkflowPhase(WorkflowPhase workflowPhase, ExecutionContext executionContext) throws WorkflowException {
		ContractAssert.preCondition(workflowPhase != null, "workflowPhase is null");
		ContractAssert.preCondition(executionContext != null, "executionContext is null");
		log.debug("Start workflowPhase : " + workflowPhase.getName() + " by actor : " + workflowPhase.getActor());
		
		//Execute actions
		List<ActionHandler> actionHandlers = workflowPhase.getActionHandlers().getActionHandler();
		for (ActionHandler actionHandler: actionHandlers) {
			String actionType = actionHandler.getType();
		    Action action = actionFactory.getAction(actionType);
			if (action == null) {
				String exceptionMessage = "Action not found for type:" + actionType;
				log.error(exceptionMessage);
				throw new WorkflowException(exceptionMessage);
			}
			action.execute(workflowPhase, executionContext);
		}
		
		// Dispatch results
		String resultDispatcherType = workflowPhase.getResultDispatcher().getType();
		ResultDispatcher resultDispatcher = resultDispatcherFactory.getResultDispatcher(resultDispatcherType);
		if (resultDispatcher == null) {
			String exceptionMessage = "ResultDispatcher not found for type:" + resultDispatcherType;
			log.error(exceptionMessage);
			throw new WorkflowException(exceptionMessage);
		}
		resultDispatcher.dispatch(workflowPhase, executionContext);
		
		log.debug("End workflowPhase : " + workflowPhase.getName() + " by actor : " + workflowPhase.getActor());
		
	}
}
