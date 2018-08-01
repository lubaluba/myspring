package org.litespring.context.impl;

import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

	public FileSystemXmlApplicationContext(String configFile) {
		super(configFile);
	}
	
	@Override
	protected Resource getResource(String path) {
		return new FileSystemResource(path);
	}

}
