package org.litespring.test.v4;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.litespring.core.impl.PackageResourceLoader;
import org.litespring.core.io.Resource;

public class PackageResourceLoaderTest {

	@Test
	public void testGetResources() throws IOException {
		PackageResourceLoader loader = new PackageResourceLoader();
		Resource[] resource = loader.getResources("org.litespring.dao.v4");
		assertEquals(2, resource.length);
	}
}
