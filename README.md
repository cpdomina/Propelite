Propelite
=========

Simple access to property files in Java

### Example

With this property file:
	name=Propelite
	version=1.0
	
And this Java Interface:
	interface App {
		public String name();
		public double version();
	}
	
We can easily access the values in the property file:
	App propelite = Propelite.create(App.class, properties);
	System.out.println(propelite.name() +" "+ propelite.version());		//Propelite 1.0
	
### More control

Need more control on what's been mapped to what? Check out this example!

Property file:
	app.propelite.name=Propelite
	app.propelite.internal=true
	
Java Interface:
	@PropertyPath(path="app")
	interface App {
		public String name();
		
		@Property(defaultValue="1.0")
		public String version();
	}
	
	@PropertyPath(path="propelite")
	interface PropeliteApp extends App{
	
		@Property(name="internal", defaultValue="false")
		public boolean someInternalValue;
	}
	
Access:
	PropeliteApp propelite = Propelite.create(PropeliteApp.class, properties);
	System.out.println(propelite.name() +" "+ propelite.version() +" "+propelite.someInternalValue);		//Propelite 1.0 true
	
### More examples

Check out the test suite in the source code to check out more examples.