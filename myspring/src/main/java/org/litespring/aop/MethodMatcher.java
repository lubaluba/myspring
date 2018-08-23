package org.litespring.aop;

import java.lang.reflect.Method;

/**
 * 	该接口主要是验证我们所传的方法是否符合表达式
 * 	在AOP中就是用该接口的实现类来判断当前方法是否需要拦截
 */
public interface MethodMatcher {
	
	boolean matches(Method method);
}
