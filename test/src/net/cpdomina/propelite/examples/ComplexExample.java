package net.cpdomina.propelite.examples;

import java.util.Properties;

import net.cpdomina.propelite.Propelite;
import net.cpdomina.propelite.Property;
import net.cpdomina.propelite.PropertyPath;

public class ComplexExample {


	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("app.propelite.name", "Propelite");
		properties.put("app.propelite.internal", "true");
		
		PropeliteApp propelite = Propelite.create(PropeliteApp.class, properties);
		System.out.println(propelite.name() +" "+ propelite.version() +" "+ propelite.someInternalValue());       //Propelite 1.0 true
	}
	
	
	@PropertyPath(path="app")
	interface App {

	    public String name();

	    @Property(defaultValue="1.0")
	    public String version();
	}

	@PropertyPath(path="propelite")
	interface PropeliteApp extends App{

	    @Property(name="internal", defaultValue="false")
	    public boolean someInternalValue();
	}
}
