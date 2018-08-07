package org.litespring.core.type.classreading;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.litespring.core.io.Resource;
import org.litespring.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;
/**
 *	实现MetadataReader,通过组合来隐藏具体的asm读取类的过程
 *	只要最后返回信息即可。	
 *	接收一个Resource即一个类,然后将这个类解析,获得类和类上注解的详细信息。
 */
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
