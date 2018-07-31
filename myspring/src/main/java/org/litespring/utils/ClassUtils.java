package org.litespring.utils;

/**
 * 	@author a3325
 *	获取类加载器的工具类
 *	这是从Spring的ClassUtil中copy了getDefaultClassLoader
 *		该方法先获得当前线程的类加载器,如果为空就去拿当前类的类加载,如果还没拿到就去拿根加载器(bootstarp ClassLoader)
 */
public abstract class ClassUtils {
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtils.class.getClassLoader();
			if (cl == null) {
				// getClassLoader() returning null indicates the bootstrap ClassLoader
				try {
					cl = ClassLoader.getSystemClassLoader();
				}
				catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the caller can live with null...
				}
			}
		}
		return cl;
	}
}