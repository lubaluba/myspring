package org.litespring.beans;

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
