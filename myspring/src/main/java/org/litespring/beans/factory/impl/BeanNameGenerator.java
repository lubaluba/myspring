package org.litespring.beans.factory.impl;

import org.litespring.beans.BeanDefinition;
/**
 *	基于注解的配置可能会不含beanName,但是我们在生成bean的时候需要beanName
 *	利用该接口提供的功能,我们在注册BeanDefinition时,通过类名首字母小写的方式自动生成beanName;
 */
public interface BeanNameGenerator {

	String generateBeanName(BeanDefinition definition, BeanDefinitionRegisty registry);
}
