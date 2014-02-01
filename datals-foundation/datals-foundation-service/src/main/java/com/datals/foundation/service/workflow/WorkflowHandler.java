package com.datals.foundation.service.workflow;

import com.intersections.ibis.common.platform.PlatformConstants;
import com.intersections.ibis.common.platform.entities.Event;
import com.intersections.ibis.common.platform.entities.Message;
import com.intersections.ibis.common.platform.services.workflow.bind.Case;
import com.intersections.ibis.common.platform.services.workflow.bind.WorkflowPhase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The reason behind this class is that we don't want to link the execution context with the Event or any other specific platform class.
 */
public class WorkflowHandler {

	private WorkflowHandler() {
		
	}

    public static void setContextEvent(ExecutionContext context, Event event) {
        context.setAttribute(PlatformConstants.EVENT_KEY, event);
    }

    public static Event getContextEvent(ExecutionContext context) {
        return (Event) context.getAttribute(PlatformConstants.EVENT_KEY);
    }

    public static Message getContextMessage(ExecutionContext context) {
        Event event = (Event) context.getAttribute(PlatformConstants.EVENT_KEY);
        if (event != null) {
            return event.getMessage();
        }
        return null;
    }

    public static String getWorkflowId(ExecutionContext context) {
        Event event = getContextEvent(context);
        return event.getMessage().getPayload().get(PlatformConstants.WORKFLOW_ID_KEY);
    }

    public static Map<String, Case> getCaseMap(WorkflowPhase workflowPhase) {
        List<Case> cases = workflowPhase.getResultDispatcher().getCase();

        Map<String, Case> caseMap = new HashMap<String, Case>();
        for (Case resultCase : cases) {
            caseMap.put(resultCase.getName(), resultCase);
        }
        return caseMap;
    }
}
