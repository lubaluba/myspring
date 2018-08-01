package org.litespring.beans.factory.config;
/**
 * 	@author a3325
 *	同RuntimeBeanReference一样,TypedStringValue用于表示注入的String属性
 */
public class TypedStringValue {
	
	private String value;
	
	public TypedStringValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}

