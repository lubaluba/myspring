package org.litespring.test.v5;

import java.lang.reflect.Method;

import org.junit.Test;
import org.litespring.service.v5.PetStoreService;
import org.litespring.tx.TransactionManager;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.NoOp;

/**
 *	该测试用例用于测试cglib
 */
public class CGLibTest {
	
	/**
	 * cglib代理是基于继承式代理,通过Enhancer创建目标类的一个子类
	 * Enhancer相当于一个Factory,是一个增强者角色,对方法进行增强。
	 */
	@Test
	public void testCallBack(){
		Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);
        //设置拦截器
        enhancer.setCallback(new TransactionInterceptor());
        PetStoreService petStore = (PetStoreService)enhancer.create();
        petStore.placeOrder();
	}
	
	public static class TransactionInterceptor implements MethodInterceptor {
		TransactionManager txManager = new TransactionManager();
	    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
	        
	        txManager.start();
	        Object result = proxy.invokeSuper(obj, args);
	        txManager.commit();      
	        return result;
	    }
	}
	
	@Test
	public void  testFilter(){
		
		Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);
        
        enhancer.setInterceptDuringConstruction(false);
        
        Callback[] callbacks = new Callback[]{new TransactionInterceptor(),NoOp.INSTANCE};
        
        Class<?>[] types = new Class<?>[callbacks.length];
		for (int x = 0; x < types.length; x++) {
			types[x] = callbacks[x].getClass();
		}
		
		
		
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);
        
        
        PetStoreService petStore = (PetStoreService)enhancer.create();
        petStore.placeOrder();
        System.out.println(petStore.toString());
        
	}
	private static class ProxyCallbackFilter implements CallbackFilter {	

		public ProxyCallbackFilter() {			
			
		}
		
		public int accept(Method method) {
			if(method.getName().startsWith("place")){
				return 0;
			} else{
				return 1;
			}
			
		}

	}
}
