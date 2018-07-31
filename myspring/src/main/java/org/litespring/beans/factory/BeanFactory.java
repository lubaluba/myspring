package org.litespring.beans.factory;
/**
 * 	@author a3325
 *	Bean的工厂,根据单一职责设计原则,他只需要提供唯一功能,getBean()
 */
public interface BeanFactory {
	Object getBean(String BeanId);
}
