package org.litespring.beans.factory.annotation;

import java.lang.reflect.Field;

import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.exception.BeanCreationException;
import org.litespring.utils.ReflectionUtils;
/**
 * 	bean中每一个被autowire修饰的field就是一个AutowiredFieldElement实体
 */
public class AutowiredFieldElement extends InjectionElement {

	boolean required;
	
	public AutowiredFieldElement(Field f, boolean required, AutowireCapableBeanFactory factory) {
		super(f, factory);
		this.required = required;
	}
	
	public Field getField() {
		return (Field)this.member;
	}

	@Override
	public void inject(Object target) {
		
		Field field = this.getField();
		try {
			DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
			Object value =factorty.resolveDependency(desc);
			
			if (value != null) {
				ReflectionUtils.makeAccessible(field);
				field.set(target, value);
			}
		} catch (Throwable ex) {
			throw new  BeanCreationException("Could not autowire field: " + field, ex);
		}
		
	}

}
