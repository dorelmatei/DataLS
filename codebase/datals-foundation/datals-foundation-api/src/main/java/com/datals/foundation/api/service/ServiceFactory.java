package com.datals.foundation.api.service;

import com.datals.foundation.model.service.LocalService;

/**
 * Constructs <code>LocalService</code> instances.
 * 
 * @author Iulian Boanca
 *
 */
public interface ServiceFactory {

	/**
	 * Gets a LocalService instance based on the given <code>serviceName</code>
	 * 
	 * @param serviceName the name of the service for which the instance is expected
	 * @return an instance of LocaleService for the given service name
	 */
	LocalService getServiceInstance(String serviceName);
}
