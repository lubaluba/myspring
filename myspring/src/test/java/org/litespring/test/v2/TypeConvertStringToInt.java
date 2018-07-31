package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.impl.SimpleTypeConverter;
import org.litespring.exception.TypeMismatchException;

public class TypeConvertStringToInt {
	TypeConverter converter;
	@Before
	public void setUp() {
		converter = new SimpleTypeConverter();
	}
	@Test
	public void testConverterStringToInt() {
		Integer i = converter.convertIfNecessary("3", Integer.class);
		assertEquals(3, i.intValue());
	}
	@Test
	public void testIllegalInteger() {
		
		try {
			converter.convertIfNecessary("3.1", Integer.class);
		} catch (TypeMismatchException e) {
			return;
		}
	}
	
	@Test
	public void testConvertStringToBoolean() {
		
		Boolean b = converter.convertIfNecessary("true", Boolean.class);
		assertEquals(true, b.booleanValue());
		
		Boolean c = converter.convertIfNecessary("yes", Boolean.class);
		assertEquals(true, c.booleanValue());
		
		Boolean d = converter.convertIfNecessary("on", Boolean.class);
		assertEquals(true, d.booleanValue());
		
		Boolean e = converter.convertIfNecessary("1", Boolean.class);
		assertEquals(true, e.booleanValue());
	}
	@Test
	public void testIllegalStringToBoolean() {
		
		try {
			converter.convertIfNecessary("xxxxx", Boolean.class);
		} catch (TypeMismatchException e) {
			return;
		}
		Assert.fail();
		
	}
}
