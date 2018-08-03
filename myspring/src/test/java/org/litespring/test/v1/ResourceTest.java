package org.litespring.test.v1;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

public class ResourceTest {
	@Test
	public void testClassPathResource() {
		Resource r = new ClassPathResource("petstore-v1.xml");
		
		try(InputStream in = r.getInputStream()){
			assertNotNull(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFileSystemResource() {
		
		Resource r = new FileSystemResource("src\\test\\resources\\petstore-v1.xml");
		
		try(InputStream in = r.getInputStream()){
			assertNotNull(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
