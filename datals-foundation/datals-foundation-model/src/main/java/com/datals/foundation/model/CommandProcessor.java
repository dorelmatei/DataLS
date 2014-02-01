package com.datals.foundation.model;

/**
 * 
 * @author <a href="mailto:dorelmatei@yahoo.com">Dorel Matei</a>
 */
public interface CommandProcessor extends LifeCycleControl {
	
    void processCommand(Command command, CommandContext commandContext) ;
}
