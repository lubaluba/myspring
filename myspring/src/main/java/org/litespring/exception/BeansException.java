package org.litespring.exception;

/**
 * 	@author a3325
 *	继承至RuntimeException,是所有创建bean过程中发生的异常的父类
 */
public class BeansException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public BeansException(String msg) {
		super(msg);
	}
	public BeansException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
