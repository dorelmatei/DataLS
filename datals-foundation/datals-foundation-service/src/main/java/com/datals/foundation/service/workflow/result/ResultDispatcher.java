package com.datals.foundation.service.workflow.result;

import com.intersections.ibis.common.platform.services.workflow.ExecutionContext;
import com.intersections.ibis.common.platform.services.workflow.WorkflowException;
import com.intersections.ibis.common.platform.services.workflow.bind.WorkflowPhase;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public interface ResultDispatcher {

    public static final String SUCCESS_CASE = "success";
    public static final String ERROR_CASE = "error";

    void dispatch(WorkflowPhase workflowPhase, ExecutionContext executionContext) throws WorkflowException;
}
