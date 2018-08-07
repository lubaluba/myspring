package org.litespring.test.v4;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.LinkedList;

import org.junit.Test;
import org.litespring.beans.factory.annotation.AutowiredFieldElement;
import org.litespring.beans.factory.annotation.InjectionElement;
import org.litespring.beans.factory.annotation.InjectionMetadata;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.service.v4.PetStoreService;

public class InjectionMetadataTest extends TestInitialize {
	
	Field f;
	InjectionElement injectionElement;
	
	@Test
	public void testInjection() throws Exception {
		
		initFactory();
		Class<?> clz = PetStoreService.class;
		LinkedList<InjectionElement> elements = new LinkedList<>();
		
		{
			doSomething("accountDao");
			elements.add(injectionElement);
		}
		{
			doSomething("itemDao");
			elements.add(injectionElement);
		}
		
		InjectionMetadata metadata = new InjectionMetadata(clz, elements);
		
		PetStoreService petStore = new PetStoreService();
		metadata.inject(petStore);

		assertTrue(petStore.getAccountDao() instanceof AccountDao);
		assertTrue(petStore.getItemDao() instanceof ItemDao);		
	}
	
	private void doSomething(String fieldName) throws Exception {
		f = PetStoreService.class.getDeclaredField(fieldName);
		injectionElement = new AutowiredFieldElement(f, true, factory);
	}
}
