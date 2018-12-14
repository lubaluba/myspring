package org.litespring.beans;
/**
 *	spring的xml配置中的每一个<property>属性都会被转换为一个PropertyValue对象
 *	解析完成后,创建bean时,就从BeanDefinition取出转换为实例
 *	@param name : property中的name
 *	@param value: 根据property中的value来判定,如果是value
 *	就生成一个TypedStringValue(String类型的对象),在根据之后将其转换为应该的类型如int
 *	如果value的值为ref,就生成一个RuntimeBeanReference(表明依赖于其他bean,会在加载时动态生成)	
 *	目前实现就支持这些,在spring中还支持list,set等多种类型
 *	@param converted : value是不是需要转换
	@param convertedValue : 保存转换后的具体值,比如RuntimeBeanReference就是具体的bean
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
