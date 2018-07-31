package org.litespring.beans.factory.impl;

import org.litespring.beans.BeanDefinition;

public interface BeanDefinitionRegisty {
	BeanDefinition getBeanDefinition(String beanId);
	void registerBeanDefinition(String beanId, BeanDefinition bd);
}
