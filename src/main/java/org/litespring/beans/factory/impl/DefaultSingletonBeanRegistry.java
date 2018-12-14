package org.litespring.beans.factory.impl;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.factory.config.SingletonBeanRegistry;
import org.litespring.utils.Assert;
/**
 * 	DefaultBeanFactory的实现类,主要是是给DefaultBeanFactory提供服务,让其可以获取singleton的class
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
	
	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		Assert.notNull(beanName, " 'beanName' must not be null");
		Object oldObject = this.singletonObjects.get(beanName);
		if (oldObject != null ) {
			throw new IllegalStateException("could not register object [" + singletonObject + "] under bean name '"
					+ beanName + "': there is already object [" + oldObject + "] bound");
		}
		this.singletonObjects.put(beanName, singletonObject);
	}

	@Override
	public Object getSingleton(String beanName) {
		return this.singletonObjects.get(beanName);
	}

}
