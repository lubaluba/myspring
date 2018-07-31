package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.impl.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.beans.impl.BeanDefinitionValueResolver;
import org.litespring.core.io.ClassPathResource;
import org.litespring.dao.AccountDao;

public class BeanDefinitionValueResolverTest {
	
	DefaultBeanFactory factory;
	XmlBeanDefinitionReader reader;
	BeanDefinitionValueResolver resolver;
	@Before
	public void SetUp() {
		factory = new DefaultBeanFactory();
		reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));
		resolver = new BeanDefinitionValueResolver(factory);
	}
	@Test
	public void testResolverRuntimeBeanReference() {
		RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
		Object value = resolver.resolverValueIfNecessary(reference);
		assertNotNull(value);
		assertTrue(value instanceof AccountDao);
	}
	@Test
	public void testResolverTypeStringValue() {
		TypedStringValue stringValue = new TypedStringValue("test");
		Object value = resolver.resolverValueIfNecessary(stringValue);
		assertNotNull(value);
		assertEquals("test", value);
	}
}
