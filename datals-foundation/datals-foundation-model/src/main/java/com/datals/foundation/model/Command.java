package com.datals.foundation.model;

/**
 * 
 * @author <a href="mailto:dorelmatei@yahoo.com">Dorel Matei</a>
 */
public interface Command {

    void execute(CommandContext commandContext);
}
