package org.litespring.test.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.impl.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.exception.BeanCreationException;
import org.litespring.exception.BeanDefinitionStoreException;
import org.litespring.service.v1.PetStoreService;

public class BeamFactoryTest {
	
	DefaultBeanFactory factory = null;
	XmlBeanDefinitionReader reader = null;
	
	@Before
	public void setUP() {
		factory = new DefaultBeanFactory();
		reader = new XmlBeanDefinitionReader(factory);
	}
	@Test
	public void testGetBean() {
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
		BeanDefinition bd =factory.getBeanDefinition("petStore");
		assertTrue(bd.isSingleton());
		assertFalse(bd.isPrototype());
		assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());
		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
		PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
		assertNotNull(petStore);
		PetStoreService petStore1 = (PetStoreService)factory.getBean("petStore");
		assertTrue(petStore.equals(petStore1));
	}
	@Test
	public void testInvalidBean() {
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
		try {
			factory.getBean("invalidBean");
		}catch (BeanCreationException e) {
			return;
		}
		Assert.fail("except BeanCreationException");
	}
	
	@Test
	public void testInvalidXml() {
		try {
			reader.loadBeanDefinitions(new ClassPathResource("petSotre-v1.xml"));
		}catch(BeanDefinitionStoreException e) {
			return;
		}
		Assert.fail("except BeanDefinitionStoreException");
	}
}
