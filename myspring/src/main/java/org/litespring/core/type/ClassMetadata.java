package org.litespring.core.type;
/**
 * 	@author a3325
 *	该接口是顶级接口,主要提供访问被asm解析后的一些类信息
 *	不用去关注asm具体解析方法
 */
public interface ClassMetadata {

	/**
	 * @return 返回注解类的名字,便于用BeanFactory去创建实例
	 */
	String getClassName();
	/**
	 * 该类是不是一个接口
	 */
	boolean isInterface();
	/**
	 * 该类是否是抽象的
	 */
	boolean isAbstract();
	/**
	 * 该类是否被final修饰
	 */
	boolean isFinal();
	/**
	 * 该类是否继承自其他类
	 */
	boolean hasSuperClass();
	/**
	 * 获得其超类的名字
	 */
	String getSuperClassName();
	/**
	 * 获得其实现的接口名
	 */
	String[] getInterfaceNames();
	
}
