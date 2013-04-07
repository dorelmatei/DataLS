/*
* (C) Copyright 2013 DataLS and contributors.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the GNU Lesser General Public License
* (LGPL) version 2.1 which accompanies this distribution, and is available at
* http://www.gnu.org/licenses/lgpl.html
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
* See the GNU Lesser General Public License for more details.
*
* Contributors:
* Dorel Matei
*/
package com.datals.foundation.model.statemachine.api;

import java.util.Set;

import com.datals.foundation.model.base.Manageable;

/**
 * 
 *
 */
public interface StateMachine extends Manageable{
	
	Set<State> getStates();
	
	Set<Transition> getTransitions();
	
	State getCurrentState();
	
	boolean triggerEvent(StateEvent stateEvent);
	
	void addStateChangeListener(StateChangeListener listener);
	
	void removeStateChangeListener(StateChangeListener listener);
}
