package com.datals.foundation.service.workflow.bind;

// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.30 at 06:05:01 PM EEST 
//


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Event"/>
 *         &lt;element ref="{}ActionHandlers"/>
 *         &lt;element ref="{}ResultDispatcher"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="actor" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "event",
    "actionHandlers",
    "resultDispatcher"
})
@XmlRootElement(name = "WorkflowPhase")
public class WorkflowPhase {

    @XmlElement(name = "Event", required = true)
    protected Event event;
    @XmlElement(name = "ActionHandlers", required = true)
    protected ActionHandlers actionHandlers;
    @XmlElement(name = "ResultDispatcher", required = true)
    protected ResultDispatcher resultDispatcher;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String name;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String actor;

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link Event }
     *     
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link Event }
     *     
     */
    public void setEvent(Event value) {
        this.event = value;
    }

    /**
     * Gets the value of the actionHandlers property.
     * 
     * @return
     *     possible object is
     *     {@link ActionHandlers }
     *     
     */
    public ActionHandlers getActionHandlers() {
        return actionHandlers;
    }

    /**
     * Sets the value of the actionHandlers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionHandlers }
     *     
     */
    public void setActionHandlers(ActionHandlers value) {
        this.actionHandlers = value;
    }

    /**
     * Gets the value of the resultDispatcher property.
     * 
     * @return
     *     possible object is
     *     {@link ResultDispatcher }
     *     
     */
    public ResultDispatcher getResultDispatcher() {
        return resultDispatcher;
    }

    /**
     * Sets the value of the resultDispatcher property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultDispatcher }
     *     
     */
    public void setResultDispatcher(ResultDispatcher value) {
        this.resultDispatcher = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the actor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActor() {
        return actor;
    }

    /**
     * Sets the value of the actor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActor(String value) {
        this.actor = value;
    }

}