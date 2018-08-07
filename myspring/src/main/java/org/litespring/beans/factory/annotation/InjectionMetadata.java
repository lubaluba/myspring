package org.litespring.beans.factory.annotation;

import java.util.List;
/**
 * 	autowire注解注入的源信息,拿到该类,我们就知道一个类中有哪些属性需要注入
 * 	也就是说,每一个存在autowire注解的类都应当有一个injectionMetadata
 *	targetClass:往哪个类中进行注入
 *	injectionElements:有哪些元素需要注入
 */
public class InjectionMetadata {
	
	@SuppressWarnings("unused")
	private final Class<?> targetClass;
	private List<InjectionElement> injectionElements;
	
	public InjectionMetadata(Class<?> targetClass, List<InjectionElement> injetionElements) {
		this.targetClass = targetClass;
		this.injectionElements = injetionElements;
	}
	
	public List<InjectionElement> getInjectionElements() {
		return this.injectionElements;
	}
	
	//递归注入所有元素,因为注入的元素可能又依赖于其他元素
	public void inject(Object target) {
		if(injectionElements == null || injectionElements.isEmpty()) {
			return;
		}
		for(InjectionElement ele : injectionElements) {
			ele.inject(target);
		}
	}
}
