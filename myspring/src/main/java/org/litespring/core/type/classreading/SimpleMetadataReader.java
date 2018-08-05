package org.litespring.core.type.classreading;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.litespring.core.io.Resource;
import org.litespring.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;

public class SimpleMetadataReader implements MetadataReader{

	private final Resource resource;
	
	private final ClassMetadata classMedata;
	
	private final AnnotationMetadata annotationMetadata;
	
	public SimpleMetadataReader(Resource resource) throws IOException {
		ClassReader classReader;
		try(InputStream in = new BufferedInputStream(resource.getInputStream())){
			classReader = new ClassReader(in);
		}
		
		AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
		classReader.accept(visitor, ClassReader.SKIP_DEBUG);
		
		this.resource = resource;
		this.classMedata = visitor;
		this.annotationMetadata = visitor;
	
	}
	@Override
	public Resource getResource() {
		return this.resource;
	}

	@Override
	public ClassMetadata getClassMetada() {
		return this.classMedata;
	}

	@Override
	public AnnotationMetadata getAnnotationMetadata() {
		return this.annotationMetadata;
	}

}
