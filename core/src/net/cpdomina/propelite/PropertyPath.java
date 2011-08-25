package net.cpdomina.propelite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PropertyPath {
	
	/**
	 * The (hierachical) path of the {@link Property Properties} in this interface
	 * @return
	 */
	String path();
}
