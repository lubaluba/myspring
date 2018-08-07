package org.litespring.core.type.classreading;

import java.util.Map;

import org.litespring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;
/**
 *	继承自ASM的AnnotationVisitor,该是当读取到注解之后,就会来反复读取该类,直到将注解属性读取完。
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {
	
	private final String annotationType;
	private final Map<String, AnnotationAttributes> attributsMap;
	
	AnnotationAttributes attributes = new AnnotationAttributes();
	
	public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
		super(SpringAsmInfo.ASM_VERSION);
		this.annotationType = annotationType;
		this.attributsMap = attributesMap;
	}
	
	@Override
	public void visitEnd() {
		this.attributsMap.put(this.annotationType, this.attributes);
	}
	
	@Override
	public void visit(String attributeName, Object attributeValue) {
		this.attributes.put(attributeName, attributeValue);
	}

}
