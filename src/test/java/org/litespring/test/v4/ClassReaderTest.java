package org.litespring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.litespring.core.type.classreading.ClassMetadataReadingVisitor;
import org.springframework.asm.ClassReader;

public class ClassReaderTest {

	ClassPathResource resource;
	ClassReader reader;
	
	@Before
	public void setUp() throws IOException {
		resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");
		reader = new ClassReader(resource.getInputStream());
	}
	@Test
	public void testGetClassMetadata() throws IOException {
		
		ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
		
		reader.accept(visitor, ClassReader.SKIP_DEBUG);
		
		assertFalse(visitor.isAbstract());
		assertFalse(visitor.isInterface());
		assertFalse(visitor.isFinal());
		assertEquals("org.litespring.service.v4.PetStoreService", visitor.getClassName());
		assertEquals("java.lang.Object", visitor.getSuperClassName());
		assertEquals(0, visitor.getInterfaceNames().length);
	}
	
	@Test
	public void testGetAnnonation() throws Exception {
		AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
		
		reader.accept(visitor, ClassReader.SKIP_DEBUG);
		
		String annotation = "org.litespring.stereotype.Component";
		assertTrue(visitor.hasAnnotation(annotation));
		
		AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);
		assertEquals("petStore", attributes.get("value"));
	}
}
