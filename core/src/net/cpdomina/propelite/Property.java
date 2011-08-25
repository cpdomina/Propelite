package net.cpdomina.propelite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Property {

	/**
	 * Name of this property in the property file
	 * @return
	 */
	String name() default "";
	
	/**
	 * The default value of this property, if not found in the property file
	 * @return
	 */
	String defaultValue() default "";
}
