package org.litespring.context.impl;

import org.litespring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.impl.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;
import org.litespring.exception.NoSuchBeanDefinitionException;
import org.litespring.utils.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext {
	
	private DefaultBeanFactory factory = null;
	private ClassLoader beanClassLoader;
	
	public AbstractApplicationContext(String configFile) {
		factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = this.getResource(configFile);
		reader.loadBeanDefinitions(resource);
		factory.setBeanClassLoader(this.getBeanClassLoader());
		registerBeanPostProcessors(factory);
	}
	
	protected abstract Resource getResource(String path);
	
	@Override
	public Object getBean(String BeanId) {
		return factory.getBean(BeanId);
	}	
	
	public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader == null ? ClassUtils.getDefaultClassLoader() : this.beanClassLoader); 
	}

	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}
	
	protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {
		AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
		postProcessor.setBeanFactory(beanFactory);
		beanFactory.addBeanPostProcessor(postProcessor);
	}
	
	public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return this.factory.getType(name);
	}
}
