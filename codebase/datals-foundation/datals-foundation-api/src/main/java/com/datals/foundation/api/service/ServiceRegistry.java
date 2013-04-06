package com.datals.foundation.api.service;

import java.util.Set;

/**
 * 
 * @author dorel
 *
 */
public interface ServiceRegistry {

	void registerService(String serviceName, String endPoint);
	
	void registerService(String serviceName, Set<String> endPoints);
	
	void unregisterService(String serviceName);
	
	Set<String> getServiceEndPoints(String serviceName);
}
