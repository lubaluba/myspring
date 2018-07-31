package org.litespring.beans;

import java.util.List;

/**
 * 	@author a3325
 *	核心接口,主要用于封装的类的定义
 *	每一个Bean都应该有一个BeanDefinition的实现类
 *		1,beanClassName:bean的名字,beanClassLoader通过该类反射来获取该类的class
 *		2,scope,类的作用域,目前主要是单例(singleton)和多例(prototype)。
 */
public interface BeanDefinition {
	
	public static final String SCOPE_SINGLETON = "singleton";
	public static final String SCOPE_PROTOTYPE = "prototype";
	public static final String SCOPE_DEFAULT = "";
	
	public boolean isSingleton();
	public boolean isPrototype();
	String getScope();
	void setScope(String scope);
	
	String getBeanClassName();
	 
	public List<PropertyValue> getPropertyValues();
}
