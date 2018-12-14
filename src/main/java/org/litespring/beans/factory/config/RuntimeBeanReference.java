package org.litespring.beans.factory.config;
/**
 * 	<bean id="xxx" class="xxx">
 * 		<property name="xxx" ref="xxx">
 * 	</bean>
 *	Spring解析阶段,容器中是没有依赖的bean的实例
 *	所以在依赖bean被用RuntimeBeanReference对象表示,当我们创建bean之后,
 *		就需要将依赖转换为spring容器中真实存在的bean
 *	该类只表示bean的name,在依赖注入时通过beanName转换为bean
 */
public class RuntimeBeanReference {

	private final String beanName;
	
	public RuntimeBeanReference(String beanName) {
		this.beanName = beanName;
	}
	
	public String getBeanName() {
		return this.beanName;
	}
}
