package org.litespring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.context.annotation.ScannedGenericBeanDefinition;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.classreading.AnnotationMetadata;
import org.litespring.stereotype.Component;

public class XmlBeanDefinitionReaderTest  extends TestInitialize {

	BeanDefinition bd;
	ScannedGenericBeanDefinition sbd;
	AnnotationMetadata amd;
	@Test
	public void testParseScanedBean () {
		
		initFactory();
		String annotation = Component.class.getName();
		
		{
			doSomething("petStore");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);	
			assertTrue(amd.hasAnnotation(annotation));
			AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
			assertEquals("petStore", attributes.get("value"));
		}
		{
			doSomething("accountDao");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);	
			assertTrue(amd.hasAnnotation(annotation));
		}
		{
			doSomething("itemDao");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);	
			assertTrue(amd.hasAnnotation(annotation));
		}
		
	}
	private void doSomething(String beanName) {
		bd = factory.getBeanDefinition(beanName);
		sbd = (ScannedGenericBeanDefinition)bd;
		amd = sbd.getMetadata();
	}
}
