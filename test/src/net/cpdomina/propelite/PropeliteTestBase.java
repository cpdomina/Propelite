package net.cpdomina.propelite;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class PropeliteTestBase {
	
	@Test
	public void testSimple() {		
		Properties properties = new Properties();
		properties.setProperty("a", "a");
		
		Simple simple = Propelite.create(Simple.class, properties);
		assertEquals(simple.a(), "a");
	}
	
	private interface Simple {
		public String a();
	}
	
	
	@Test
	public void testDefault() {
		Default def = Propelite.create(Default.class);
		assertEquals(def.a(), "a");
	}
	
	private interface Default {
		@Property(defaultValue="a")
		public String a();
	}
	
	@Test
	public void testName() {
		Properties properties = new Properties();
		properties.setProperty("someName", "a");
		
		Name name = Propelite.create(Name.class, properties);
		assertEquals(name.a(), "a");
	}
	
	private interface Name {
		@Property(name="someName")
		public String a();
	}
	
	@Test
	public void testPath() {
		Properties properties = new Properties();
		properties.setProperty("some.path.a", "a");
		
		Path path = Propelite.create(Path.class, properties);
		assertEquals(path.a(), "a");
	}

	@PropertyPath(path="some.path")
	private interface Path {
		public String a();
	}
	
	@Test
	public void testPathHierarchy() {
		Properties properties = new Properties();
		properties.setProperty("some.path.a", "a");
		properties.setProperty("some.path.b", "b");
		
		PathHierarchy hierarchy = Propelite.create(PathHierarchy.class, properties);
		assertEquals(hierarchy.a(), "a");
		assertEquals(hierarchy.b(), "b");
	}

	@PropertyPath(path="some")
	private interface PathSuper {
		public String a();
	}
	
	@PropertyPath(path="path")
	private interface PathHierarchy extends PathSuper{
		public String b();
	}

}
