package org.litespring.context.impl;

import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
/**
 * 	@author a3325
 *	ApplicationContext的一种具体实现,主要是通过classPath下的xml文件来创建ApplicationContext
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
	
	public ClassPathXmlApplicationContext(String configFile) {
		super(configFile);
	}
	
	protected Resource getResource(String path) {
		return new ClassPathResource(path,this.getBeanClassLoader());
	}
 
}
