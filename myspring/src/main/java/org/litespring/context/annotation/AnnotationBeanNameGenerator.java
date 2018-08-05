package org.litespring.context.annotation;
import java.beans.Introspector;
import java.util.Set;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring.beans.factory.impl.BeanDefinitionRegisty;
import org.litespring.beans.factory.impl.BeanNameGenerator;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.classreading.AnnotationMetadata;
import org.litespring.utils.ClassUtils;
import org.litespring.utils.StringUtils;

public class AnnotationBeanNameGenerator implements BeanNameGenerator {
	
	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegisty registry) {
		if (definition instanceof AnnotatedBeanDefinition) {
			String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition)definition);
			if(StringUtils.hasText(beanName)) {
				return beanName;
			}
		}
		return buildDefaultBeanName(definition);
	}
	
	//检查注解是否含有value属性,含有就是用value的值作为beanName
	protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition definition) {
		AnnotationMetadata amd = definition.getMetadata();
		Set<String> types = amd.getAnnotationTypes();
		String beanName = null;
		for (String type : types) {
			AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
			if(attributes.get("value") != null) {
				Object value = attributes.get("value");
				if(value instanceof String) {
					String strVal = (String)value;
					if(StringUtils.hasLength(strVal)) {
						beanName = strVal;
					}
				}
			}
		}
		return beanName;
	}
	
	//如果注解中不含value属性,那就注入默认的值(也就是类名首字母小写)
	protected String buildDefaultBeanName(BeanDefinition definition) {
		//将全类名转换为简单的短类名
		String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
		//首字母小写
		return Introspector.decapitalize(shortClassName);
	}
}
