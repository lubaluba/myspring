package org.litespring.test.v1;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.impl.ClassPathXmlApplicationContext;
import org.litespring.context.impl.FileSystemXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;
public class ApplicationContextTest {

	@Test
	public void testGetBean() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		assertNotNull(petStore);
	}
	
	@Test
	public void testGetBeanFormFileSystemContext() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src\\test\\resources\\petstore-v1.xml");
		PetStoreService petService = (PetStoreService)ctx.getBean("petStore");
		assertNotNull(petService);
	}
}
