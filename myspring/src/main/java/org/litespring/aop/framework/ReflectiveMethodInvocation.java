package org.litespring.aop.framework;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 *	@param targetObject : 目标类,也就是我们需要将通知织入的类(petStoreService)
 *	@param targetMethod : 目标方法,被织入的具体方法(placeOrder)
 *	@param arguments : 目标方法的具体参数类型列表
 *	@param interceptors : 方法拦截器,每一个advice类都是一个拦截器(实现了MethodInterceptor接口)
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
	
	protected final Object targetObject;

	protected final Method targetMethod;

	protected Object[] arguments;

	protected final List<MethodInterceptor> interceptors;

	/**
	 * 记录目前执行了的拦截器,每执行一个MethodInterceptor的invoke()方法,该值+1
	 */
	private int currentInterceptorIndex = -1;

	public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments, List<MethodInterceptor> interceptors) {
		this.targetObject = target;
		this.targetMethod = method;
		this.arguments = arguments;
		this.interceptors = interceptors;
	}

	public final Object getThis() {
		return this.targetObject;
	}

	public final Method getMethod() {
		return this.targetMethod;
	}

	public final Object[] getArguments() {
		return (this.arguments != null ? this.arguments : new Object[0]);
	}
	
	/**
	 * 这里采用了递归调用,按顺序调用每一个MethodInterceptor的invoke方法
	 * 直到所有的MethodInterceptor执行完毕,就执行当前method;
	 */
	public Object proceed() throws Throwable {
		//	所有的拦截器已经调用完成
		if (this.currentInterceptorIndex == this.interceptors.size() - 1) {
			return invokeJoinpoint();
		}

		this.currentInterceptorIndex ++;
		
		MethodInterceptor interceptor = this.interceptors.get(this.currentInterceptorIndex);
		
		return interceptor.invoke(this);
	}
	
	/**
	 * 该方法是用于调用targetObject的method,也就是目标方法,通过proceed()让其在合适的时间调用
	 * @return 返回的是Joinpoint
	 */
	protected Object invokeJoinpoint() throws Throwable {		
		return this.targetMethod.invoke(this.targetObject, this.arguments);
	}

	public AccessibleObject getStaticPart() {		
		return this.targetMethod;
	}
}