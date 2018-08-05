package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeanDefinition;
import org.litespring.core.type.classreading.AnnotationMetadata;
/**
 *	该接口继承了BeanDefinition,表明这是一个基于注解的bean的Definition
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

	AnnotationMetadata getMetadata();
}
