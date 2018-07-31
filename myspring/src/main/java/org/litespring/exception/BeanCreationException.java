package org.litespring.exception;

public class BeanCreationException extends BeansException{

	private static final long serialVersionUID = 1L;

	public BeanCreationException(String msg, Throwable cause) {
		super(msg, cause);
	}
	public BeanCreationException(String msg) {
		super(msg);
	}

}
