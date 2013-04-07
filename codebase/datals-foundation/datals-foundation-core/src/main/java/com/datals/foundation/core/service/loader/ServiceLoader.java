package com.datals.foundation.core.service.loader;

import java.net.URL;
import java.util.List;

/**
 * Loads Service annotated classes
 * 
 * @author Iulian Boanca
 *
 */
public interface ServiceLoader {

	/**
	 * Loads Service annotated classes from the specified <code>location</code>.
	 * 
	 * @param location the jar file location under the form of URL
	 * @return returns the Service annotated classes
	 */
	List<Class<?>> loadServiceClasses(URL location);

}
