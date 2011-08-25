package net.cpdomina.propelite;

import java.lang.reflect.Proxy;
import java.util.Properties;

public class Propelite {

	private Propelite() {};
	
	/**
	 * Create a Proxy Instance of the given <code>type</code> using its default values
	 * @param <T>
	 * @param type
	 * @return
	 */
	public static <T> T create(Class<T> type) {
		return create(type, new Properties());
	}
	
	/**
	 * Create a Proxy Instance of the given <code>type</code>, mapping its values using the given {@link Properties} file
	 * @param <T>
	 * @param type
	 * @param properties
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> type, Properties properties) {
		return (T)Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, new PropeliteHandler(properties, type));
	}
	
}
