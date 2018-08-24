package org.litespring.beans.factory.config;

import org.litespring.exception.BeansException;

/**
 * 	bean初始化过程中行为的接口,也就是在bean的生命周期过程中
 * 	bean的初始化的过程中我们想自动的去执行一些任务
 * 	这些任务可以在初始化之前也可以在初始化之后
 * 	这样的任务又叫钩子函数
 * 	我们使用的autowireAnnotationProcessor来完成注入就是一个钩子函数,所以要实现该接口
 */
public interface BeanPostProcessor {
	
	//bean初始化之前执行
	Object beforeInitialization(Object bean, String  beanName) throws BeansException;
	
	//bean初始化之后执行
	Object afterInitialization(Object bean, String beanName) throws BeansException;
}
