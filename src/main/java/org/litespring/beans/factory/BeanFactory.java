package org.litespring.beans.factory;

import org.litespring.exception.NoSuchBeanDefinitionException;

/**
 *	Bean的工厂,根据单一职责设计原则,他只需要提供唯一功能,getBean()
 */
public interface BeanFactory {
	
	/**
	 * @param xml中配置的bean的id
	 * @return 返回一个bean的对象
	 */
	Object getBean(String BeanId);
	
	/**
	 *  根据beanName获得一个bean的类型,也就是class
	 * @param name 传入beanName
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	Class<?> getType(String name) throws NoSuchBeanDefinitionException;
}
