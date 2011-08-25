package net.cpdomina.propelite.examples;

import java.util.Properties;

import net.cpdomina.propelite.Propelite;

public class SimpleExample {

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("name", "Propelite");
		properties.put("version", "1.0");
		
		App propelite = Propelite.create(App.class, properties);
		System.out.println(propelite.name() +" "+ propelite.version());     //Propelite 1.0
	}
	
	
	static interface App {
	    public String name();
	    public double version();
	}
}
