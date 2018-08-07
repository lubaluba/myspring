 package org.litespring.context.annotation;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.impl.BeanDefinitionRegisty;
import org.litespring.beans.factory.impl.BeanNameGenerator;
import org.litespring.core.impl.PackageResourceLoader;
import org.litespring.core.io.Resource;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;
import org.litespring.exception.BeanDefinitionStoreException;
import org.litespring.stereotype.Component;
import org.litespring.utils.StringUtils;

/**
 *	找出xml中配置的需要扫描的包,对指定package进行扫描
 *	找到带注解的类,创建ScannedGenericBeanDefinition,并注册到BeanFactory
 */
public class ClassPathBeanDefinitionScanner {

	private final BeanDefinitionRegisty registy;
	
	private PackageResourceLoader resourceLoader = new PackageResourceLoader();
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
	
	public ClassPathBeanDefinitionScanner(BeanDefinitionRegisty registy) {
		this.registy = registy;
	}
	
	public Set<BeanDefinition> doScan(String packagesToScan) {
		
		String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan, ",");
		Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
		for (String basePackage : basePackages) {
			Set<BeanDefinition> candidates = findCandidateComponets(basePackage);
			for (BeanDefinition candidate : candidates) {
				beanDefinitions.add(candidate);
				registy.registerBeanDefinition(candidate.getID(), candidate);
			}
		}
		return beanDefinitions;
	}

	public Set<BeanDefinition> findCandidateComponets(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
		try {
			Resource[] resources = this.resourceLoader.getResources(basePackage);
			for (Resource resource : resources) {
				try {
					MetadataReader metadataReader = new SimpleMetadataReader(resource);
					if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
						ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
						String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registy);
						sbd.setId(beanName);
						candidates.add(sbd);
					}
				} catch (Throwable ex) {
					throw new BeanDefinitionStoreException("Failed to read candidate component class: " + resource, ex);
				}	
			}
		} catch (IOException ex) {
			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
		}
		
		return candidates;
	}

}
