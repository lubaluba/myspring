package org.litespring.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 *  @author a3325
 *	构造器注入使用的类,用一个list来保存xml解析后需要构造器注入的参数
 *	该类使用了一个静态内部类ValueHolder来保存参数,ValueHolder的value可以是类和任意类型的变量,也就是xml中的ref和value
 *	使用ValueHolder可以很好的支持多种注入方式,比如按照参数name,按照参数的index,按照参数的type
 *	这里是简易实现,只实现用name注入 
 */
public class ConstructorArgument {
	
	private final List<ValueHolder> argumentValues = new LinkedList<>();
	
	public void addArgumentValue(Object value, String type) {
		addArguentValue(new ValueHolder(value, type));
	}
	public void addArguentValue(ValueHolder valueHolder) {
		this.argumentValues.add(valueHolder);
	
	}
	
	public List<ValueHolder> getArgumentValues() {
		return	Collections.unmodifiableList(this.argumentValues);
	}
	
	public int getArgumentCount() {
		return this.argumentValues.size();
	}
	
	public boolean isEmpty() {
		return this.argumentValues.isEmpty();
	}
	
	public static class ValueHolder {
		
		private Object value;
		private String type;
		private String name;
		
		public ValueHolder(Object value) {
			this.value = value;
		}
		public ValueHolder(Object value, String type) {
			this.value = value;
			this.type = type;
		}
		public ValueHolder(Object value, String type, String name) { 
			this.value = value;
			this.type = type;
			this.name = name;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
