package com.datals.foundation.service.workflow.bind;

// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.30 at 06:05:01 PM EEST 
//


import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Workflow }
     * 
     */
    public Workflow createWorkflow() {
        return new Workflow();
    }

    /**
     * Create an instance of {@link ActionHandler }
     * 
     */
    public ActionHandler createActionHandler() {
        return new ActionHandler();
    }

    /**
     * Create an instance of {@link Event }
     * 
     */
    public Event createEvent() {
        return new Event();
    }

    /**
     * Create an instance of {@link ActionHandlers }
     * 
     */
    public ActionHandlers createActionHandlers() {
        return new ActionHandlers();
    }

    /**
     * Create an instance of {@link Case }
     * 
     */
    public Case createCase() {
        return new Case();
    }

    /**
     * Create an instance of {@link ResultDispatcher }
     * 
     */
    public ResultDispatcher createResultDispatcher() {
        return new ResultDispatcher();
    }

    /**
     * Create an instance of {@link Workflows }
     * 
     */
    public Workflows createWorkflows() {
        return new Workflows();
    }

    /**
     * Create an instance of {@link WorkflowPhase }
     * 
     */
    public WorkflowPhase createWorkflowPhase() {
        return new WorkflowPhase();
    }

}
