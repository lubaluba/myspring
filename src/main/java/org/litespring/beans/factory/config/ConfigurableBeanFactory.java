package org.litespring.beans.factory.config;

import java.util.List;

public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
	ClassLoader getBeanClassLoader();
	void setBeanClassLoader(ClassLoader	beanClassLoader);
	
	/**
	 * 完成了BeanPostProcessor后,我们就需要找地方注入该bpp。
	 */
	void addBeanPostProcessor(BeanPostProcessor postProcessor);
	
	/**
	 *  除了autowire注解的processor,一个beanfactor可能还有多个其他processor
	 */
	List<BeanPostProcessor> getBeanPostProcessors();
}
