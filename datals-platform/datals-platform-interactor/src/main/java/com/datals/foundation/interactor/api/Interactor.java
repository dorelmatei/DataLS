package com.datals.foundation.interactor.api;

import com.intersections.ibis.interactor.api.client.ClientContext;
import com.intersections.ibis.platform.LifeCycle;

/**
 * Wrapper component interface over services API exposing operations that might take long time.
 * Interacting through this instead of directly calling the services API enables 
 * more control over what happens during service execution, and increases the services scalability :
 * <ul>
 *    <li>Clients are not required to be available during the task execution. 
 *    They can register, submit tasks, unregister than come back later to harvest the results.</li>
 *    <li>Submitting tasks and receiving results is done asynchronously using queues.</li>
 *    <li>Clients can receive intermediate feedback about progress of operations.</li>
 *    <li>Clients can decide to cancel an ongoing task.</li>
 * </ul>
 *  
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public interface Interactor extends LifeCycle {

	void registerClient(ClientContext clientContext);
	
	void unregisterClient(String clientId);
	
	boolean isRegisteredClient(String clientId);

	void submitTask(Task task);

	void cancelTask(String taskId);
}
