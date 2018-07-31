package org.litespring.beans.impl;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.impl.DefaultBeanFactory;

public class BeanDefinitionValueResolver {
	
	private final DefaultBeanFactory beanFactory;
	
	public BeanDefinitionValueResolver(DefaultBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public Object resolverValueIfNecessary(Object value) {
		
		if(value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref = (RuntimeBeanReference)value;
			String refName = ref.getBeanName();
			Object bean = beanFactory.getBean(refName);
			return bean;
		}else if(value instanceof TypedStringValue) {
			return ((TypedStringValue)value).getValue();
		}else {
			throw new RuntimeException("the value " + value + " has not implemented");
		}
	}
	
	
}

