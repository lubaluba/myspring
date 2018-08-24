package org.litespring.aop;

/**
 *	PointCut(切入点)是指明Advice（增强）所作用的地方（一般指方法）。
 *	PointCut简单来说是一个基于表达式的拦截条件。
 */
public interface Pointcut {
	
	MethodMatcher getMethodMatcher();
	String getExpression();
}
