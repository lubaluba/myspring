package org.litespring.test.v3;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.impl.ConstructorResolver;
import org.litespring.beans.factory.impl.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v3.PetStoreService;
/**
 * 	@author a3325
 *	该类主要是用于找到正确的构造函数,并通过该构造函数创建对象
 */
public class ConstructorResolverTest {
	
	@Test
	public void testAutowireConstructor() {
		
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v3.xml"));
		
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		
		ConstructorResolver resolver = new ConstructorResolver(factory);
		
		PetStoreService petStore = (PetStoreService)resolver.autowireConstructor(bd);
		
		assertEquals(1, petStore.getVersion());
		assertNotNull(petStore.getAccountDao());
		assertNotNull(petStore.getItemDao());
		
	}
}
