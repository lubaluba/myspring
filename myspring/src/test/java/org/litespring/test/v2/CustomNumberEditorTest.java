package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomNumberEditor;

public class CustomNumberEditorTest {
	@Test
	public void test() {
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
		editor.setAsText("3");
		Object value = editor.getValue();
		assertTrue(value instanceof Integer);
		assertEquals(3, ((Integer)editor.getValue()).intValue());
		
		editor.setAsText("");
		assertTrue(editor.getValue() == null);
	}
	@Test
	public void illegalTest() {
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
		
		try {
			editor.setAsText("3.1");
		}catch(IllegalArgumentException e) {
			return; 
		}
		Assert.fail();
		
	}
}	
