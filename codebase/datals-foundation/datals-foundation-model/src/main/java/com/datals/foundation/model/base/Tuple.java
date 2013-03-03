package com.datals.foundation.model.base;

import java.util.List;

/**
 * 
 * @author dorel
 *
 */
public interface Tuple {

	List<Object> getElements();
	
	TupleDescriptor getDescriptor();
}
