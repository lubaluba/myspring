package org.litespring.core.type.classreading;

import org.litespring.core.type.ClassMetadata;
import org.litespring.utils.ClassUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;
/**
 * 	@author a3325 zlm
 * 	该类继承至asm的ClassVisitor,是一个visitor类
 * 	该类主要是交给
 */
public class ClassMetadataReadingVisitor extends ClassVisitor 
	implements ClassMetadata {

	private String className;
	
	private boolean isInterface;
	private boolean isAbstract;
	private boolean isFinal;
	
	private String superClassName;
	
	private String[] interfaces;
	
	public ClassMetadataReadingVisitor() {
		super(SpringAsmInfo.ASM_VERSION);
	}
	
	/*
	 * version:class文件编译的版本号, 
	 * access:权限修饰符,以及判断该类的类型(抽象,接口等)
	 * name:class的资源路径形式name,如dao/itemDao
	 * supername:父类名+资源路径
	 * interfaces:实现的接口名
	 * 
	*/
	@Override
	public void visit(int version, int access, String name, String arg3, String supername, String[] interfaces) {
		this.className = ClassUtils.convertResourcePathToClassName(name);
		//这里用到位运算来,access是一个代码根据位运算计算得出的
		this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
		this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
		this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
		if (supername != null) {
			this.superClassName = ClassUtils.convertResourcePathToClassName(supername);
		}
		this.interfaces = new String[interfaces.length];
		for( int i = 0; i < interfaces.length ; i++) {
			this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
		}
	}
	
	public boolean isFinal() {
		return this.isFinal;
	}
	public boolean isInterface() {
		return this.isInterface;
	}
	public boolean isAbstract() {
		return this.isAbstract;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public String getSuperClassName() {
		return this.superClassName;
	}
	public String[] getInterfaceNames() {
		return this.interfaces;
	}

	@Override
	public boolean hasSuperClass() {
		return (this.superClassName != null);
	}
}
