package org.litespring.test.v5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.impl.ClassPathXmlApplicationContext;
import org.litespring.service.v5.PetStoreService;
import org.litespring.util.MessageTracker;

public class ApplicationContextTest5 {	
	
	@Before
	public void setUp() {
		MessageTracker.clearMsgs();
	}
	
	@Test
	public void testPlaceOrder() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v5.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		
		assertNotNull(petStore.getAccountDao());
		assertNotNull(petStore.getItemDao());
		
		petStore.placeOrder();
		List<String> msgs = MessageTracker.getMsgs();
		assertEquals("start tx", msgs.get(0));
		assertEquals("place order", msgs.get(1));
		assertEquals("commit", msgs.get(2));
	}
}
