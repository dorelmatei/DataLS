package com.datals.foundation.service.workflow;

import com.intersections.ibis.common.platform.services.workflow.bind.Workflow;
import com.intersections.ibis.common.platform.services.workflow.bind.WorkflowPhase;
import com.intersections.ibis.common.platform.services.workflow.bind.Workflows;
import com.intersections.ibis.common.runtime.assertion.ContractAssert;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class WorkflowDefinition {

    private static final Logger LOG = LoggerFactory.getLogger(WorkflowDefinition.class);
    private static final String WORKFLOW_FILE = "workflow.xml";
    private static final String WORKFLOW_SCHEMA_FILE = "workflow.xsd";
    private static final String WORKFLOW_BINDING_CONTEXT_PATH = "com.intersections.ibis.common.platform.services.workflow.bind";

    private Map<String, Workflow> workflowsMap = new ConcurrentHashMap<String, Workflow>();

    public WorkflowPhase getWorkflowPhase(String workflowId, String actor, String channel, String eventType) throws WorkflowAccessException {
//		ContractAssert.preCondition(workflowId != null && !workflowId.isEmpty(), "workflowId is null or empty");
        ContractAssert.preCondition(actor != null && !actor.isEmpty(), "phaseName is null or empty");
        ContractAssert.preCondition(eventType != null && !eventType.isEmpty(), "eventType is null or empty");
        ContractAssert.preCondition(channel != null && !channel.isEmpty(), "channel is null or empty");

        if (workflowsMap.isEmpty()) {
            unmarshall();
        }

        List<WorkflowPhase> phases = new ArrayList<WorkflowPhase>();

        Workflow workflow = null;
        if (workflowId != null) {
            workflow = workflowsMap.get(workflowId);
        }

        if (workflow != null) {
            phases = getPhase(workflow, actor, channel, eventType);
        }
        else {
            //no workflow id provided, will search among all workflows for a UNIQUE workflow based on the other criterias.
            for (Workflow wf : workflowsMap.values()) {
                phases.addAll(getPhase(wf, actor, channel, eventType));
            }
        }

        if (phases.size() != 1) {
            return null;
        }

        return phases.get(0);
    }

    private List<WorkflowPhase> getPhase(Workflow workflow, String actor, String channel, String eventType) {
        List<WorkflowPhase> foundPhases = new ArrayList<WorkflowPhase>();
        List<WorkflowPhase> phases = workflow.getWorkflowPhase();
        for (WorkflowPhase phase : phases) {
            if (actor.equals(phase.getActor())) {
                if (eventType.equals(phase.getEvent().getType())) {
                    if (channel.equals(phase.getEvent().getChannel())) {
                        foundPhases.add(phase);
                    }
                }
            }
        }
        return foundPhases;
    }

    public Set<String> getChannelsForActor(String actor) throws WorkflowAccessException {
        ContractAssert.preCondition(actor != null && !actor.isEmpty(), "actor is null or empty");
        if (workflowsMap.isEmpty()) {
            unmarshall();
        }
        Set<String> channels = new HashSet<String>();
        for (Workflow workflow : workflowsMap.values()) {
            List<WorkflowPhase> phases = workflow.getWorkflowPhase();
            for (WorkflowPhase phase : phases) {
                if (actor.equals(phase.getActor())) {
                    channels.add(phase.getEvent().getChannel());
                }
            }
        }
        return channels;
    }

    public Set<String> getEventTypesForActor(String actor) throws WorkflowAccessException {
        ContractAssert.preCondition(actor != null && !actor.isEmpty(), "actor is null or empty");
        if (workflowsMap.isEmpty()) {
            unmarshall();
        }
        Set<String> eventTypes = new HashSet<String>();
        for (Workflow workflow : workflowsMap.values()) {
            List<WorkflowPhase> phases = workflow.getWorkflowPhase();
            for (WorkflowPhase phase : phases) {
                if (actor.equals(phase.getActor())) {
                    eventTypes.add(phase.getEvent().getType());
                }
            }
        }
        return eventTypes;
    }


    private void unmarshall() throws WorkflowAccessException {
        InputStream bufferedInputStream = null;
        InputStream schemaBufferedInputStream = null;
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(WORKFLOW_FILE);
            if (url == null) {
                throw new WorkflowAccessException("Cannot find file:" + WORKFLOW_FILE);
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(WORKFLOW_BINDING_CONTEXT_PATH);
            if (jaxbContext == null) {
                throw new WorkflowAccessException("Cannot create JAXBContext for the given context path: "
                        + WORKFLOW_BINDING_CONTEXT_PATH);
            }

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            if (unmarshaller == null) {
                throw new WorkflowAccessException("Cannot create Unmarshaller from the JAXBContext: " + jaxbContext);
            }

            // Validate schema 
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL schemaURL = Thread.currentThread().getContextClassLoader().getResource(WORKFLOW_SCHEMA_FILE);
            InputStream schemaInputStream = schemaURL.openStream();
            schemaBufferedInputStream = new BufferedInputStream(schemaInputStream);
            unmarshaller.setSchema(schemaFactory.newSchema(new StreamSource(schemaInputStream)));

            InputStream inputStream = url.openStream();
            bufferedInputStream = new BufferedInputStream(inputStream);
            Workflows workflows = (Workflows) unmarshaller.unmarshal(bufferedInputStream);
            if (workflows == null) {
                throw new WorkflowAccessException("Error while unmarshalling file: " + url);
            }

            // Populate workflow map
            List<Workflow> workflowList = workflows.getWorkflow();
            for (Workflow workflowItem : workflowList) {
                workflowsMap.put(workflowItem.getId(), workflowItem);
            }

        } catch (IOException | JAXBException | SAXException e) {
            LOG.error("Could not read workflow definition. ", e);
            throw new WorkflowAccessException("Could not read workflow definition. ", e);
        } finally {
            IOUtils.closeQuietly(bufferedInputStream);
            IOUtils.closeQuietly(schemaBufferedInputStream);
        }
    }

}
