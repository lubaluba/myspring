package org.litespring.core.type.classreading;

import java.util.Map;

import org.litespring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

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
