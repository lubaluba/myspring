package org.litespring.beans.factory.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.ConstructorArgument.ValueHolder;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.impl.BeanDefinitionValueResolver;
import org.litespring.beans.impl.SimpleTypeConverter;
import org.litespring.exception.BeanCreationException;

public class ConstructorResolver {
 
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final ConfigurableBeanFactory beanFactory;
	
	public ConstructorResolver(ConfigurableBeanFactory beanFatory) {
		this.beanFactory = beanFatory;
	}
	
	public Object autowireConstructor(final BeanDefinition bd) {
		
		Constructor<?> constructorToUse = null;
		Object[] argsToUse = null;
		
		Class<?> beanClass = null;
		try {
			beanClass = this.beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());
		} catch (ClassNotFoundException e) {
			throw new BeanCreationException(bd.getID(), "Instantiation of bean failed, can't resolve class", e);
		}
		
		Constructor<?>[] candidates = beanClass.getConstructors();
		Field[] fields = beanClass.getDeclaredFields();
		BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);
		
		ConstructorArgument cargs = bd.getConstructorArgument();
		SimpleTypeConverter typeConverter = new SimpleTypeConverter();
		
		fields = getMathchedFildsByName(fields, cargs.getArgumentValues());
				
		for (int i = 0; i < candidates.length; i++) {
			Class<?>[] parameterTypes = candidates[i].getParameterTypes();
			if (parameterTypes.length != cargs.getArgumentCount()) {
				continue;
			}
			
			boolean ismatchedConstructor = true;
			for(int j = 0; j < fields.length; j++) {
				if(fields[j] != null && !fields[j].getType().equals(parameterTypes[j])) {
					ismatchedConstructor = false;
				}
			}
			if(!ismatchedConstructor) {
				continue;
			}
			
			argsToUse = new Object[parameterTypes.length];
			
			boolean result = this.valuesMatchTypes(parameterTypes, cargs.getArgumentValues(), argsToUse, valueResolver, typeConverter);
			
			if (result) {
				constructorToUse = candidates[i];
				break;
			}
		}
			
		if (constructorToUse == null) {
			throw new BeanCreationException(bd.getID(), "can't find a apporiate constructor");
		}
			
		try {
			return constructorToUse.newInstance(argsToUse);
		} catch (Exception e) {
			throw new BeanCreationException(bd.getID(), "can't find a create instance using " + constructorToUse);
		}	
	}
	
	private Field[] getMathchedFildsByName(Field[] fields, List<ValueHolder> list) {
		Field[] fls = new Field[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < fields.length; j++) {
				if(fields[j].getName().equals(list.get(i).getName())) {
					fls[i] = fields[i];
					break;
				}
				fls[i] = null;
			}
		}
		
		return fls;
	}

	private boolean valuesMatchTypes(Class<?>[] parameterTypes, List<ValueHolder> valueHolders, Object[] argsToUse,
			BeanDefinitionValueResolver valueResolver, SimpleTypeConverter typeConverter) {
		
		for (int i = 0; i < parameterTypes.length; i++) {
			ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
			//获取参数的值,可能是TypedStringValue,也可以是RuntimeBeanReference
			Object originalValue = valueHolder.getValue();
			
			try {
				//获得真值
				Object resolvedValue = valueResolver.resolverValueIfNecessary(originalValue);
				//如果参数是int,但值是字符串,需要转换
				//如果转型失败就抛出异常,说明这个构造函数不可用
				Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
				
				argsToUse[i] = convertedValue;
			} catch (Exception e) {
				logger.error(e);
				return false;
			}
		}
		return true;
	}

}
