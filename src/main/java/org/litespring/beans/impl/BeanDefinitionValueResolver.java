package org.litespring.beans.impl;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
/**
 *	该类用于对BeanDifinition中的propertyValue进行解析
 *	如,一个RuntimeBeanReference将其转换为具体的bean
 * 	将RuntimeBeanReference转换为具体的bean等
 */
public class BeanDefinitionValueResolver {
	
	private final BeanFactory beanFactory;
	
	public BeanDefinitionValueResolver(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	public Object resolverValueIfNecessary(Object value) {
		if (value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref = (RuntimeBeanReference)value;
			String refName = ref.getBeanName();
			Object bean = beanFactory.getBean(refName);
			return bean;
		} else if (value instanceof TypedStringValue) {
			return ((TypedStringValue)value).getValue();
		} else {
			throw new RuntimeException("the value " + value + " has not implemented");
		}
	}
}

