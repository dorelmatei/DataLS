package com.datals.foundation.service.workflow.action;

import com.intersections.ibis.common.platform.services.workflow.ExecutionContext;
import com.intersections.ibis.common.platform.services.workflow.WorkflowException;
import com.intersections.ibis.common.platform.services.workflow.bind.WorkflowPhase;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public interface Action {

	void execute(WorkflowPhase workflowPhase, ExecutionContext executionContext) throws WorkflowException;
}
