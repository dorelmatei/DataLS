package com.datals.foundation.interactor.core;

/**
 * Listens for notifications coming from producers and consumers in order to signal that there is a <code>TaskContext</code>
 * to be consumed or there is a new consumer registered.
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public interface StatusChangeListener {

    void notifyStatusChanged();
}
