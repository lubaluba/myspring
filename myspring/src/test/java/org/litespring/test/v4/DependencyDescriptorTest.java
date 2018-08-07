package org.litespring.test.v4;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.dao.v4.AccountDao;
import org.litespring.service.v4.PetStoreService;

public class DependencyDescriptorTest extends TestInitialize{

	@Test
	public void testResolveDependency() throws Exception {
		initFactory();
		Field f = PetStoreService.class.getDeclaredField("accountDao");
		DependencyDescriptor descriptor = new DependencyDescriptor(f, true);
		Object o = factory.resolveDependency(descriptor);
		assertTrue(o instanceof AccountDao);
	}
}
