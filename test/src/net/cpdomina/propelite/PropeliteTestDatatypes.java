package net.cpdomina.propelite;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class PropeliteTestDatatypes {
	
	static enum Enm {A, B, C};

	@Test
	public void testDatatypes() {
		Properties properties = new Properties();
		properties.setProperty("a", "a");
		properties.setProperty("b", Enm.B.toString());
		properties.setProperty("c", Boolean.TRUE.toString());
		properties.setProperty("d", "d");
		properties.setProperty("e", Byte.toString(Byte.MAX_VALUE));
		properties.setProperty("f", Short.toString(Short.MAX_VALUE));
		properties.setProperty("g", Integer.toString(Integer.MAX_VALUE));
		properties.setProperty("h", Long.toString(Long.MAX_VALUE));
		properties.setProperty("i", Float.toString(Float.MAX_VALUE));
		properties.setProperty("j", Double.toString(Double.MAX_VALUE));
		properties.setProperty("k", new SomeClass("k").getValue());
		
		Datatypes data = Propelite.create(Datatypes.class, properties);
		
		assertEquals(data.a(), "a");
		assertEquals(data.b(), Enm.B);
		assertEquals(data.c(), true);
		assertEquals(data.d(), 'd');
		assertEquals(data.e(), Byte.MAX_VALUE);
		assertEquals(data.f(), Short.MAX_VALUE);
		assertEquals(data.g(), Integer.MAX_VALUE);
		assertEquals(data.h(), Long.MAX_VALUE);
		assertEquals(data.i(), Float.MAX_VALUE, 0);
		assertEquals(data.j(), Double.MAX_VALUE, 0);
		assertEquals(data.k(), new SomeClass("k"));
	}
	
	private interface Datatypes {	
		public String a();
		public Enm b();
		public boolean c();
		public char d();
		public byte e();
		public short f();
		public int g();
		public long h();
		public float i();
		public double j();
		public SomeClass k();
	}
	
	private static class SomeClass {
		private final String value;
		public SomeClass(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SomeClass other = (SomeClass) obj;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
	}
	
}
