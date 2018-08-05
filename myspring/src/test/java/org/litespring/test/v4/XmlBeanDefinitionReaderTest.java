package org.litespring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.impl.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.annotation.ScannedGenericBeanDefinition;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.core.type.classreading.AnnotationMetadata;
import org.litespring.stereotype.Component;

public class XmlBeanDefinitionReaderTest {

	@Test
	public void testParseScanedBean () {
		
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petStore-v4.xml");
		reader.loadBeanDefinitions(resource);
		String annotation = Component.class.getName();
		
		{
			BeanDefinition bd = factory.getBeanDefinition("petStore");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);	
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
			AnnotationMetadata amd = sbd.getMetadata();
			
			assertTrue(amd.hasAnnotation(annotation));
			AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
			assertEquals("petStore", attributes.get("value"));
		}
		{
			BeanDefinition bd = factory.getBeanDefinition("accountDao");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);	
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
			AnnotationMetadata amd = sbd.getMetadata();
			assertTrue(amd.hasAnnotation(annotation));
		}
		{
			BeanDefinition bd = factory.getBeanDefinition("itemDao");
			assertTrue(bd instanceof ScannedGenericBeanDefinition);	
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
			AnnotationMetadata amd = sbd.getMetadata();
			assertTrue(amd.hasAnnotation(annotation));
		}
	}
}
