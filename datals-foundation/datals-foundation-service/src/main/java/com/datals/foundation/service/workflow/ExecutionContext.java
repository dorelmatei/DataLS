package com.datals.foundation.service.workflow;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class ExecutionContext {


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
