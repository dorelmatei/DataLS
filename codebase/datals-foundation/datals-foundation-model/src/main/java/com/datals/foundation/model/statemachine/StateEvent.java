package com.datals.foundation.model.statemachine;

import java.util.Map;

/**
 * 
 * @author dorel
 *
 */
public interface StateEvent extends Identifiable {

   Map<String, Object> getPayload();
}
