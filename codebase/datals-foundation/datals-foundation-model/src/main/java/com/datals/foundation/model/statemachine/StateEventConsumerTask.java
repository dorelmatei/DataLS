package com.datals.foundation.model.statemachine;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class StateEventConsumerTask implements Runnable {

	private BlockingQueue<StateEvent> eventsQueue;
	private StateEventConsumer consumer;
	private boolean stopped;
	
	public StateEventConsumerTask() {
		
	}
	
	@Override
	public void run() {
		while (!stopped) {
			try {
				StateEvent stateEvent = eventsQueue.poll(60, TimeUnit.SECONDS);
				consumer.consume(stateEvent);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
