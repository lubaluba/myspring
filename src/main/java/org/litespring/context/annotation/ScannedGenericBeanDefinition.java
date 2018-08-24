package org.litespring.context.annotation;

import org.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring.beans.impl.GenericBeanDefinition;
import org.litespring.core.type.classreading.AnnotationMetadata;
/**
 *	扫描出来的Bean的Definition用该类来表达。
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

	private final AnnotationMetadata metadata;


	public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
		super();
		
		this.metadata = metadata;
		
		setBeanClassName(this.metadata.getClassName());
	}


	public final AnnotationMetadata getMetadata() {
		return this.metadata;
	}

}
