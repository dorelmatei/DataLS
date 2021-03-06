package com.datals.foundation.model;

/**
 * 
 * @author <a href="mailto:dorelmatei@yahoo.com">Dorel Matei</a>
 */
public interface LifeCycleControl {

    void init() throws LifeCycleControlException;
    
    void destroy() throws LifeCycleControlException;
}
