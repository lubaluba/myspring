package org.litespring.exception;

/**
 * 	@author a3325
 *	在创建bean时因为读取xml文件发生异常时抛出.
 */
public class BeanDefinitionStoreException extends BeansException {
	private static final long serialVersionUID = -644026310271892714L;

	public BeanDefinitionStoreException(String msg, Throwable cause) {
		super(msg, cause);
	}
	public BeanDefinitionStoreException(String msg) {
		super(msg);
	}


}
