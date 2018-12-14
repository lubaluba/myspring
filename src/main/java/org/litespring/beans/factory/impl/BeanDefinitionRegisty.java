package org.litespring.beans.factory.impl;

import org.litespring.beans.BeanDefinition;

public interface BeanDefinitionRegisty {
	/**
	 *	根据beanId获取BeanDefinition
	 */
	BeanDefinition getBeanDefinition(String beanId);
	
	/**
	 * 	注册BeanDefinition,通过读取xml文件,将获取的beanDefinition注册到BeanFactory
	 */
	void registerBeanDefinition(String beanId, BeanDefinition bd);
}
