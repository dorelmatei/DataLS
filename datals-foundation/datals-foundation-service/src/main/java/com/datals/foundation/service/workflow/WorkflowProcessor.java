package com.datals.foundation.service.workflow;

import com.intersections.ibis.common.platform.services.workflow.bind.WorkflowPhase;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public interface WorkflowProcessor {

	void processWorkflowPhase(WorkflowPhase phase, ExecutionContext executionContext) throws WorkflowException ;
}
