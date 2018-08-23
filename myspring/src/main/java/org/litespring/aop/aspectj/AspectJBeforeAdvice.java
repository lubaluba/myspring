package org.litespring.aop.aspectj;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
/**
 * Advice的实现类,定义了目标方法执行之前要执行的通知
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {

	public AspectJBeforeAdvice(Method adviceMethod,AspectJExpressionPointcut pointcut,Object adviceObject){
		super(adviceMethod,pointcut,adviceObject);
	}
	
	/**
	 * 	通过传入方法拦截器来调用方法
	 *	在本次的代码中也就是调用TransactionManager的方法,如start/commit等
	 *	
	 */
	public Object invoke(MethodInvocation mi) throws Throwable {
		this.invokeAdviceMethod();
		Object o = mi.proceed();
		return o;
	}
	
}
