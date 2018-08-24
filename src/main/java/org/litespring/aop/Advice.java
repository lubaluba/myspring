package org.litespring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 	通知接口,也就是我们代理类要执行的方法,其实现有多种
 * 	如beforeAdvice,AfterAdvice等
 * 	该接口继承至MethodInterceptor,也就是拦截器,
 * 	
 */
public interface Advice extends MethodInterceptor {
	
	Pointcut getPointcut();
}
