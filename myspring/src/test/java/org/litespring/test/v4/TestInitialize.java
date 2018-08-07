package org.litespring.test.v4;

import org.litespring.beans.factory.impl.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

public class TestInitialize {
	protected DefaultBeanFactory factory;

	public void initFactory() {
		factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petStore-v4.xml");
		reader.loadBeanDefinitions(resource);
	}
}
