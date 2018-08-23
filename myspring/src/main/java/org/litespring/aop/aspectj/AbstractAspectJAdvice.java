package org.litespring.aop.aspectj;

import java.lang.reflect.Method;

import org.litespring.aop.Advice;
import org.litespring.aop.Pointcut;
/**
 *	Advice接口实现类的抽象父类
 *	@param Method 我们要执行的具体方法
 *	@param AspectJExpressionPointcut 切点,我们要通过切点把通知织入到指定方法
 *	@param Object 通知的具体对象
 */
public abstract class AbstractAspectJAdvice implements Advice {
	
	protected Method adviceMethod;	
	
	protected AspectJExpressionPointcut pointcut;
	
	protected Object adviceObject;

	public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject){
		this.adviceMethod = adviceMethod;
		this.pointcut = pointcut;
		this.adviceObject = adviceObject;
	}
		
	public void invokeAdviceMethod() throws  Throwable{
		adviceMethod.invoke(adviceObject);
	}
	public Pointcut getPointcut(){
		return this.pointcut;
	}
	public Method getAdviceMethod() {
		return adviceMethod;
	}
}
