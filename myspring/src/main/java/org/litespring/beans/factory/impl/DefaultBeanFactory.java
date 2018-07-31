package org.litespring.beans.factory.impl; 
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.impl.BeanDefinitionValueResolver;
import org.litespring.beans.impl.SimpleTypeConverter;
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
		if (bd == null) {
			throw new BeanCreationException("Bean Definition does not exist");
		}
		
		if (bd.isSingleton()) {
			Object bean = this.getSingleton(beanId);
			if(bean == null) {
				bean = createBean(bd);
				this.registerSingleton(beanId, bean);
			}
			return bean;
		}
		return createBean(bd);
	}
	
	private Object createBean(BeanDefinition bd) {
		//创建实例
		Object bean = instantiateBean(bd);
		//设置属性	
		populateBean(bd, bean);
		
		return bean;
	}
	private Object instantiateBean(BeanDefinition bd) {
		ClassLoader cl = this.getBeanClassLoader();
		String beanClassName = bd.getBeanClassName();
		try {
			Class<?> clazz = cl.loadClass(beanClassName);
			return clazz.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for " + beanClassName + " failed",e);
		}
	}
	protected void populateBean(BeanDefinition bd, Object bean) {
		List<PropertyValue> pvlist = bd.getPropertyValues();
		
		if (pvlist == null || pvlist.isEmpty()) {
			return;
		}
		
		BeanDefinitionValueResolver vauleResolver = new BeanDefinitionValueResolver(this);
		SimpleTypeConverter converter = new SimpleTypeConverter();
		
		try {
			for (PropertyValue pv : pvlist) {
				String propertyName = pv.getName();
				Object originalValue = pv.getValue();
				Object resolvedValue = vauleResolver.resolverValueIfNecessary(originalValue);
				//通过javaBean提供的方法来执行bean的setter方法,来完成set注入
				BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
				PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
				for(PropertyDescriptor pd : pds) {
					if(pd.getName().equals(propertyName)) {
						Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
						//pd.getWriteMethod().invoke(bean, resolvedValue);
						pd.getWriteMethod().invoke(bean, convertedValue);
						break;
					}
				}
 			}
		}catch(Exception e) {
			throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]");
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
