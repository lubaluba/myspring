package org.litespring.beans.factory.impl; 
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.exception.BeanCreationException;
import org.litespring.utils.ClassUtils;
/**
 *  @author a3325
 *	BeanFactory的具体实现类
 *	根据单一原则,它核心方法只是getBean(),但其需要很多其他类的支持来获取BeanDefiniton,ClassLoader等	 		
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry 
	implements BeanDefinitionRegisty,ConfigurableBeanFactory{

	private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
	private ClassLoader beanClassLoader = null;
	
	@Override
	public Object getBean(String beanId) {
		BeanDefinition bd  = this.getBeanDefinition(beanId);
		if(bd == null) {
			throw new BeanCreationException("Bean Definition does not exist");
		}
		
		if(bd.isSingleton()) {
			Object bean = this.getSingleton(beanId);
			if(bean == null) {
				bean = createBean(bd);
				this.registerSingleton(beanId, bean);
			}
			return bean;
		}
		return createBean(bd);
	}
	
	public Object createBean(BeanDefinition bd) {
		ClassLoader cl = this.getBeanClassLoader();
		String beanClassName = bd.getBeanClassName();
		try {
			Class<?> clazz = cl.loadClass(beanClassName);
			return clazz.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for " + beanClassName + " failed",e);
		}
	}
	
	@Override
	public BeanDefinition getBeanDefinition(String beanId) {
		return this.beanDefinitionMap.get(beanId); 
	}

	@Override
	public void registerBeanDefinition(String beanId, BeanDefinition bd) {
		this.beanDefinitionMap.put(beanId, bd);
	}

	@Override
	public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader == null ? ClassUtils.getDefaultClassLoader() : this.beanClassLoader); 
	}

	@Override
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}
}
