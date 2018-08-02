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
	
	String SCOPE_SINGLETON = "singleton";
	String SCOPE_PROTOTYPE = "prototype";
	String SCOPE_DEFAULT = "";
	
	boolean isSingleton();
	boolean isPrototype();
	
	String getScope();
	void setScope(String scope);
	
	String getBeanClassName();
	 
	List<PropertyValue> getPropertyValues();
	ConstructorArgument getConstructorArgument();
	String getID();
	boolean hasConstructorArgumentValues();
}
