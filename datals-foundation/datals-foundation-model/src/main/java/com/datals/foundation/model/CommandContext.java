package com.datals.foundation.model;


import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * @author <a href="mailto:dorelmatei@yahoo.com">Dorel Matei</a>
 */
public class CommandContext {
    
    private ConcurrentHashMap<String, Object> contextAttributes = new ConcurrentHashMap<String, Object>();
    
    public Object getAttribute(String attrName) {
        return contextAttributes.get(attrName);
    }

    public void setAttribute(String attrName, Object attrValue) {
        contextAttributes.put(attrName, attrValue);
    }

    public void clear() {
        contextAttributes.clear();
    }
}
