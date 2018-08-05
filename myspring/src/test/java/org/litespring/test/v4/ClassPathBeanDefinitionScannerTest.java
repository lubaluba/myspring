package org.litespring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.impl.DefaultBeanFactory;
import org.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import org.litespring.context.annotation.ScannedGenericBeanDefinition;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.classreading.AnnotationMetadata;
import org.litespring.stereotype.Component;

public class ClassPathBeanDefinitionScannerTest {

	BeanDefinition bd;
	ScannedGenericBeanDefinition sbd;
	DefaultBeanFactory factory;
	AnnotationMetadata amd;
	@Test
	public void testParseScanedBean() {
		
		factory = new DefaultBeanFactory();
		
		String basePackages = "org.litespring.service.v4,org.litespring.dao.v4";
		
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
		scanner.doScan(basePackages);
		
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
	public void doSomething(String beanName) {
		bd = factory.getBeanDefinition(beanName);
		sbd = (ScannedGenericBeanDefinition)bd;
		amd = sbd.getMetadata();
	}
}
