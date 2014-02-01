package com.datals.foundation.interactor.module;

import com.datals.foundation.interactor.api.Interactor;
import com.datals.foundation.interactor.api.ProgressReporter;
import com.datals.foundation.interactor.core.ClientContextRegistry;
import com.datals.foundation.interactor.core.ConcurrentResultProcessor;
import com.datals.foundation.interactor.core.ConcurrentTaskProcessor;
import com.datals.foundation.interactor.core.InteractorConstants;
import com.datals.foundation.interactor.core.ServiceInteractor;
import com.datals.foundation.interactor.core.ServiceProgressReporter;
import com.datals.foundation.interactor.core.StatusMonitor;
import com.datals.foundation.interactor.core.TaskContextCleaner;
import com.datals.foundation.interactor.core.TaskContextStore;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.intersections.ibis.runtime.config.FilePropertiesBean;
import com.intersections.ibis.runtime.config.FilePropertiesLoader;
import com.intersections.ibis.runtime.config.PropertiesBean;
import com.intersections.ibis.runtime.config.PropertiesLoader;

/**
 * 
 * @author <a href="mailto:dorel.matei@threepillarglobal.com">Dorel Matei</a>
 */
public class InteractorModule extends AbstractModule {

	@Override
	public void configure() { 
		
		bind(ServiceInteractor.class);
		bind(Interactor.class).to(ServiceInteractor.class);
		bind(ConcurrentTaskProcessor.class);
		bind(ConcurrentResultProcessor.class);
		bind(TaskContextStore.class);
		bind(ClientContextRegistry.class);
		bind(StatusMonitor.class);
		bind(TaskContextCleaner.class);
		bind(ProgressReporter.class).to(ServiceProgressReporter.class);
		
		bind(PropertiesBean.class).to(FilePropertiesBean.class);
		bind(PropertiesLoader.class).to(FilePropertiesLoader.class);
		bind(String.class).annotatedWith(Names.named("FileResource")).toInstance(InteractorConstants.INTRERACTOR_PROPERTY_FILE);
		
		//bind(ClientResultConsumer.class).to(BridgeResultConsumer.class);
		
		
		
    }  
}
