package net.cpdomina.propelite;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Handler for the invocation of methods in {@link Propelite}
 * @author Pedro Oliveira
 *
 */
public class PropeliteHandler implements InvocationHandler {

	private final Properties properties;
	private final Class<?> type;

	public PropeliteHandler(Properties properties, Class<?> type) {
		this.properties = properties;
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		final String path = processPath(getPath(type));

		final Property annotation = method.getAnnotation(Property.class);
		String propertyName = (annotation != null) ? annotation.name().trim() : "";
		final String defaultValue = (annotation != null) ? annotation.defaultValue().trim() : "";	

		if(propertyName.length() == 0) {	
			//No name annotation on property, check for getX() method
			final String methodName = method.getName();
			if(methodName.length() > 3 && methodName.startsWith("get")) {
				propertyName = methodName.substring(3);
				propertyName = Character.toLowerCase(propertyName.charAt(0))+propertyName.substring(1);
			} else {
				propertyName = methodName;
			}
		}		

		final String propertyValue = properties.getProperty(path+propertyName, defaultValue);
		return parse(method.getReturnType(), propertyValue);
	}

	/**
	 * Add a . to the end of a {@link PropertyPath}, if needed
	 * @param path
	 * @return
	 */
	private String processPath(String path) {
		if(path != null) {
			if(path.length() > 0) {
				return path.concat(".");
			} else {
				return path;
			}	
		}else {
			return "";
		}
	}

	/**
	 * Get the full path for the current property
	 * @param type
	 * @return
	 */
	private String getPath(Class<?> type) {
		PropertyPath annotation = type.getAnnotation(PropertyPath.class);
		String path = annotation != null ? annotation.path().trim() : "";	
		
		Class<?>[] interfaces = type.getInterfaces();
		for(Class<?> interf: interfaces) {
			String spath = getPath(interf);
			if(path != null && path.length() > 0) {
				return spath+"."+path;
			}
		}
		return path;
	}

	/**
	 * Parse the given value into an Object of the given type
	 * @param type
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object parse(Class<?> type, String value) {

		if(type.equals(String.class)) {
			return value;
		}
		else if(type.isEnum()) {
			return Enum.valueOf((Class<? extends Enum>) type, value);
		}
		else if(type.isPrimitive()) {
			if(type.equals(Void.TYPE) || type.equals(Void.class)) {
				return null;
			}
			else if(type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
				return Boolean.parseBoolean(value);
			}
			else if(type.equals(Character.TYPE) || type.equals(Character.class)) {
				return value.charAt(0);
			}
			else if(type.equals(Byte.TYPE) || type.equals(Byte.class)) {
				return Byte.parseByte(value);
			}
			else if(type.equals(Short.TYPE) || type.equals(Short.class)) {
				return Short.parseShort(value);
			}
			else if(type.equals(Integer.TYPE) || type.equals(Integer.class)) {
				return Integer.parseInt(value);
			}
			else if(type.equals(Long.TYPE) || type.equals(Long.class)) {
				return Long.parseLong(value);
			}
			else if(type.equals(Float.TYPE) || type.equals(Float.class)) {
				return Float.parseFloat(value);
			}
			else if(type.equals(Double.TYPE) || type.equals(Double.class)) {
				return Double.parseDouble(value);
			}
			else {
				throw new UnsupportedOperationException(String.format("Method (%s) return type not supported for reading argument values.", type));
			}
		}
		else if(type.equals(Character.class)) {
			if(value.length() == 1) {
				return value.charAt(0);
			}
			else {
				throw new UnsupportedOperationException(String.format("Value is not a character (%s)", value));
			}
		}
		else {
			//Build object		
			try {
				final Constructor<?> constructor = type.getConstructor(new Class[]{String.class});
				return constructor.newInstance(new Object[]{value});
			} catch (Exception e) {
				throw new UnsupportedOperationException(String.format("Unable to create object of type (%s). Missing String constructor?", type));
			}
		}
	}

}
