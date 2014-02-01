package com.datals.foundation.interactor.core;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.intersections.ibis.interactor.api.client.ClientContext;
import com.intersections.ibis.interactor.core.processor.StatusMonitor;
import com.intersections.ibis.runtime.assertion.ContractAssert;

/**
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
@Singleton
public class ClientContextRegistry {
	
	private static final Logger log = LoggerFactory.getLogger(ClientContextRegistry.class);
	
	private Map<String, ClientContext> registry = new ConcurrentHashMap<String, ClientContext>();
	
	@Inject
	private StatusMonitor taskStatusMonitor;
	
	public void registerClientContext(ClientContext clientContext) {
		 ContractAssert.preCondition(clientContext != null, "clientContext is null");
		 registry.put(clientContext.getClientId(), clientContext);
		 synchronized (taskStatusMonitor) {
			 taskStatusMonitor.incrementUpdateCounter();
			 taskStatusMonitor.notify();
	     }
		 log.debug("ClientContext registered : " + clientContext);
	}
	
	public ClientContext unregisterClientContext(String clientId) {
		ClientContext clientContext = registry.remove(clientId);
		log.debug("ClientContext unregistered : " + clientContext);
		return clientContext;
	}
	
	public ClientContext getClientContext(String clientId) {
		return registry.get(clientId);
	}
	
	public Set<String> getRegisteredClientIds() {
		return registry.keySet();
	}
	
	public boolean isRegisteredClient(String clientId) {
		return registry.containsKey(clientId);
	}
	
	public void clear() {
		registry.clear();
		log.debug("ClientContextRegistry cleared.");
	}

}
