package org.litespring.beans;
/**
 * 	@author a3325
 *	spring的xml配置中的每一个<property>属性都会被转换为一个PropertyValue对象
 *	解析完成后,创建bean时,就从BeanDefinition取出转换为实例
 *	name分别代表property的naem
 *	value代表property的值,主要有RuntimeBeanReference(依赖其他bean),
 *		TypedStringValue(String类型)等
 */
public class PropertyValue {
	
	private final String name;
	private final Object value;
	private boolean converted = false;
	private Object convertedValue;
	
	public PropertyValue(String name, Object value) {
		this.name  = name;
		this.value = value;
	}

	public synchronized boolean isConverted() {
		return converted;
	}

	public synchronized void setConverted(boolean converted) {
		this.converted = converted;
	}

	public Object getConvertedValue() {
		return convertedValue;
	}

	public void setConvertedValue(Object convertedValue) {
		this.convertedValue = convertedValue;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
	
	
}
