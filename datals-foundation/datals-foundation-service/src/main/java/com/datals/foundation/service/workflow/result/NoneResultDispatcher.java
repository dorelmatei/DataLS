package com.datals.foundation.service.workflow.result;

import com.intersections.ibis.common.platform.services.workflow.ExecutionContext;
import com.intersections.ibis.common.platform.services.workflow.WorkflowException;
import com.intersections.ibis.common.platform.services.workflow.WorkflowHandler;
import com.intersections.ibis.common.platform.services.workflow.bind.WorkflowPhase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class NoneResultDispatcher implements ResultDispatcher {

    private static final Logger log = LoggerFactory.getLogger(NoneResultDispatcher.class);

    @Override
    public void dispatch(WorkflowPhase workflowPhase, ExecutionContext executionContext) throws WorkflowException {
        String workflowId = WorkflowHandler.getWorkflowId(executionContext);
        log.debug("No result dispatch for workflow : {} and phase : {}", workflowId, workflowPhase.getName());
    }

}
