package com.datals.foundation.model.statemachine;


import java.util.concurrent.BlockingQueue;

import com.datals.foundation.model.base.Producer;



public class StateEventProducer implements Producer<StateEvent>{

	private BlockingQueue<StateEvent> eventsQueue;
	
	public StateEventProducer() {
		
	}

	@Override
	public void produce(StateEvent stateEvent) {
		try {
			eventsQueue.put(stateEvent);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
