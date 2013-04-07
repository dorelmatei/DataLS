package com.datals.foundation.model.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Local Service annotation that allows a class to be loaded as a service and invoked as one.
 * 
 * @author Iulian Boanca
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {

	/**
	 * The name of the service. Make sure that this is unique.
	 * 
	 * @return the name that identifies the service.
	 */
	String name() default "";
	
}
