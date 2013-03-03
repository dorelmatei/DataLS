package com.datals.foundation.model.service;

import java.util.Set;

/**
 * 
 * @author dorel
 *
 */
public interface ServiceRegistryProxy {

	void registerService(String serviceName, String endPoint);
	
	void unregisterServiceEndPoint(String serviceName);
	
	Set<String> getServiceEndPoints(String serviceName);
}
