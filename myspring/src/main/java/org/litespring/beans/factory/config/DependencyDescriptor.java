package org.litespring.beans.factory.config;

import java.lang.reflect.Field;

import org.litespring.utils.Assert;
/**
 *	该类代表我们使用autowire创建bean时需要依赖的字段
 *	当我们传入一个参数,可以是构造函数,字段和setter方法,该类就能返回我们需要注入的bean的类型
 *	只有field和required两个字段,如果是被依赖的字段,就在注入bean的时候将依赖也注入进去
 *	目前只支持Field字段注入,spring还支持构造器和setter的注入,这里没有给出实现。
 */
public class DependencyDescriptor {

	private Field field;
	private boolean required;
	
	public DependencyDescriptor(Field field, boolean required) {
		Assert.notNull(field, "Field must not be null");
		this.field = field;
		this.required = required;
	}
	
	//返回被依赖的类型
	public Class<?> getDependencyType() {
		if (this.field != null) {
			return field.getType();
		}
		throw new RuntimeException("only support field dependency");
	}
	
	public boolean isRequired() {
		return this.required;
	}
}	
