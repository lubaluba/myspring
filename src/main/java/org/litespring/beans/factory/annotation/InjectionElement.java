package org.litespring.beans.factory.annotation;

import java.lang.reflect.Member;

import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
/**
 * 	该类代表autowire类中需要注入的元素,使用抽象表达注入的属性。
 * 	因为该类的实现都接受同样的参数：member和factory,所以这是一个抽象的类。
 * 	该类有三个具体实现,分别对应Field注入的元素,setter注入元素和构造函数注入元素
 * 	目前只实现field注入(AutowiredFieldElement) 
 */
public abstract class InjectionElement {

	//Member来自于reflect包,表示该成员可以是一个Filed,method或者一个constructor
	protected Member member;
	protected AutowireCapableBeanFactory factorty;
	
	public InjectionElement(Member member, AutowireCapableBeanFactory factorty) {
		this.member = member;
		this.factorty = factorty;
	}
	public abstract void inject(Object target);
}
