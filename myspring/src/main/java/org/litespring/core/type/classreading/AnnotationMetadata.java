package org.litespring.core.type.classreading;

import java.util.Set;

import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.ClassMetadata;
/**
 * 	有了该接口,就可以隐藏注解解析的具体方法,只提供对注解信息的访问
 * 	继承自ClassMetadata,并对其扩展
 */
public interface AnnotationMetadata extends ClassMetadata {

	/**
	 * @return 获得某类中存在的所有注解类型
	 */
	Set<String> getAnnotationTypes();
	/**
	 * @return 该类中是否包含某一注解
	 */
	boolean hasAnnotation(String annotationType);
	/**
	 * @return 返回某一注解的所有属性
	 */
	AnnotationAttributes getAnnotationAttributes(String annotationType);
}
