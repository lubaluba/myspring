package org.litespring.aop.config;

import java.lang.reflect.Method;

import org.litespring.beans.factory.BeanFactory;
import org.litespring.utils.BeanUtils;
import org.litespring.utils.StringUtils;
/**
 *	通过Ponintcu和MethodMather接口对需要拦截的方法进行匹配并拦截后就需要通过该类定位方法。
 *	在aop的时候我们就是通过代理,将我们需要运行在拦截方法运行周期中的
 *	事务方法,这些事务方法其实也是一些bean,<aop:aspect ref="tx">,那么我们通过该beanName以及
 *	其方法名start,定位到start()方法。然后在合适的时机反射调用
 *	
 *	该类实现的功能就是方法的定位
 *	@param targetBeanName 我们需要织入的通知Advice类的beanName
 *	@param methodName 方法名
 *	@param method 根据方法名和beanName拿到的method
 */
public class MethodLocatingFactory {
	
	private String targetBeanName;

	private String methodName;

	private Method method;
	
	public void setTargetBeanName(String targetBeanName) {
		this.targetBeanName = targetBeanName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 *  通过beanFactory反射获得class,再由BeanUtils获得方法,要保证在这之前methodName和beanName已经传入
	 */
	public void setBeanFactory(BeanFactory beanFactory) {
		if (!StringUtils.hasText(this.targetBeanName)) {
			throw new IllegalArgumentException("Property 'targetBeanName' is required");
		}
		if (!StringUtils.hasText(this.methodName)) {
			throw new IllegalArgumentException("Property 'methodName' is required");
		}

		//拿到class对象
		Class<?> beanClass = beanFactory.getType(this.targetBeanName);
		if (beanClass == null) {
			throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName + "'");
		}
		
		this.method = BeanUtils.resolveSignature(this.methodName, beanClass);

		if (this.method == null) {
			throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
					"] on bean [" + this.targetBeanName + "]");
		}
	}


	public Method getObject() throws Exception {
		return this.method;
	}

}