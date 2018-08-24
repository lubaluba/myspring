package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;
/**
 *	autowire注解实现自动注入时使用的beanFactory
 *	专门用于处理注解实体并创建bean的factory接口,defaultBeanFactory通过
 *	实现该接口就可以扩展得到创建autowire的bean的功能,也是面向接口编程的体现
 */
public interface AutowireCapableBeanFactory extends BeanFactory{
	
	Object resolveDependency(DependencyDescriptor descriptor);
}
