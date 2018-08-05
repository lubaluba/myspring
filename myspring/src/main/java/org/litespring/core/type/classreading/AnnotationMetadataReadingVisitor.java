package org.litespring.core.type.classreading;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.litespring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;

public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor 
	implements AnnotationMetadata {
	
	//记录这个类里面有哪些注解,这里只保存种类,不保存数目,用set
	private final Set<String> annotationTypeSet = new LinkedHashSet<>(4);
	//因为AnnotataionAttributes本身就是一个map,这里就保存了每个注解的所有属性
	private final Map<String, AnnotationAttributes> attributeMap = new LinkedHashMap<>(4);
	
	//desc:注解的详情,就是具体的注解类
	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		
		String className = Type.getType(desc).getClassName();
		this.annotationTypeSet.add(className);
		return new AnnotationAttributesReadingVisitor(className, this.attributeMap);
	}
	
	public Set<String> getAnnotationTypes() {
		return this.annotationTypeSet;
	}
	
	public boolean hasAnnotation(String annotationType) {
		return annotationType.contains(annotationType);
	}
	
	public AnnotationAttributes getAnnotationAttributes(String annotationType) {
		return this.attributeMap.get(annotationType);
	}
}
