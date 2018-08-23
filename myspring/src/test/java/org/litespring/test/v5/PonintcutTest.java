package org.litespring.test.v5;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;
import org.litespring.aop.MethodMatcher;
import org.litespring.aop.aspectj.AspectJExpressionPointcut;
import org.litespring.service.v5.PetStoreService;

public class PonintcutTest {
	
	@Test
	public void testPonintcut() throws Exception	 {
		String expression = "execution(* org.litespring.service.v5.*.placeOrder(..))";
		AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
		pc.setExpression(expression);
		
		MethodMatcher mm = pc.getMethodMatcher();
		
		{
			Class<?> targetClass = PetStoreService.class;
			
			Method method1 = targetClass.getMethod("placeOrder");
			assertTrue(mm.matches(method1));
			
			Method method2 = targetClass.getMethod("getAccountDao");
			assertFalse(mm.matches(method2));
		}
		
		{
			Class<?> targetClass = org.litespring.service.v4.PetStoreService.class;
			
			Method method1 = targetClass.getMethod("getAccountDao");
			assertFalse(mm.matches(method1));
		}
	}
}
