package org.litespring.aop.aspectj;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.weaver.reflect.ReflectionWorld.ReflectionWorldException;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParameter;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;
import org.litespring.aop.MethodMatcher;
import org.litespring.aop.Pointcut;
import org.litespring.utils.ClassUtils;
import org.litespring.utils.StringUtils;

public class AspectJExpressionPointcut implements Pointcut, MethodMatcher {

	private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();

	static {
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
	}
	
	private String expression;
	
	//这里使用的aspectj的核心类中的PonintcutExpression,该类就是切点表达式类
	private  PointcutExpression pointcutExpression;

	private ClassLoader pointcutClassLoader;
	
	public AspectJExpressionPointcut(){
		
	}
	
	//判断传入的方法是否匹配表达式,从而绝地是否需要拦截
	@Override
	public boolean matches(Method method) {
		
		checkReadyToMatch();
		
		ShadowMatch shadowMatch = getShadowMatch(method);
		
		if (shadowMatch.alwaysMatches()) {
			return true;
		}
		
		return false;
	}
	@Override
	public MethodMatcher getMethodMatcher() {
		
		return this;
	}
	
	private ShadowMatch getShadowMatch(Method method) {
		
		ShadowMatch shadowMatch = null;
		try {
			shadowMatch = this.pointcutExpression.matchesMethodExecution(method);
		}
		catch (ReflectionWorldException ex) {
			
			throw new RuntimeException("not implemented yet");
			/*try {
				fallbackExpression = getFallbackPointcutExpression(methodToMatch.getDeclaringClass());
				if (fallbackExpression != null) {
					shadowMatch = fallbackExpression.matchesMethodExecution(methodToMatch);
				}
			}
			catch (ReflectionWorldException ex2) {
				fallbackExpression = null;
			}*/
		}
		return shadowMatch;
	}
	
	private void checkReadyToMatch() {
		if (getExpression() == null) {
			throw new IllegalStateException("Must set property 'expression' before attempting to match");
		}
		if (this.pointcutExpression == null) {			
			this.pointcutClassLoader = ClassUtils.getDefaultClassLoader();
			this.pointcutExpression = buildPointcutExpression(this.pointcutClassLoader);
		}
	}

	private PointcutExpression buildPointcutExpression(ClassLoader classLoader) {
				
		PointcutParser parser = PointcutParser
				.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
						SUPPORTED_PRIMITIVES, classLoader);
		
		/*PointcutParameter[] pointcutParameters = new PointcutParameter[this.pointcutParameterNames.length];
		for (int i = 0; i < pointcutParameters.length; i++) {
			pointcutParameters[i] = parser.createPointcutParameter(
					this.pointcutParameterNames[i], this.pointcutParameterTypes[i]);
		}*/
		return parser.parsePointcutExpression(replaceBooleanOperators(getExpression()),
				null, new PointcutParameter[0]);
	}

	private String replaceBooleanOperators(String pcExpr) {
		String result = StringUtils.replace(pcExpr, " and ", " && ");
		result = StringUtils.replace(result, " or ", " || ");
		result = StringUtils.replace(result, " not ", " ! ");
		return result;
	}
	
	@Override
	public String getExpression() {
		
		return this.expression;
	}
	
	public void setExpression(String expression){
		this.expression = expression;
	}
	
}