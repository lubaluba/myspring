package org.litespring.beans.factory.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring.core.annotation.AnnotationUtils;
import org.litespring.exception.BeanCreationException;
import org.litespring.exception.BeansException;
import org.litespring.stereotype.Autowired;
import org.litespring.utils.ReflectionUtils;
/**
 * 	autowiredAnnotationProcessor可以把一个class变成injectionMetadata
 * 
 * 	我们要实现自动注入,那么首先就要拿到一个类的injectionMetadata,这样我们才能丢给factory去解析并生成相应的bean
 * 	我们可以通过asm读取到了类的字段,和注解,也通过拿到带autowire注解并通过inject()方法注入进去
 * 	这个类需要实现的就是当我们拿到一个类的时候,如果拿到这个类的injectionMetadata
 * 	这就是buildAutowiringMetadata(class<?> clazz)要实现的功能
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor  {
	
	private AutowireCapableBeanFactory beanFactory;
	private String requiredParameterName = "required";
	private boolean requiredParameterValue = true;
	
	private final Set<Class<? extends Annotation>> autowiredAnnotationTypes =
			new LinkedHashSet<Class<? extends Annotation>>();
	
	public AutowiredAnnotationProcessor(){
		this.autowiredAnnotationTypes.add(Autowired.class);
	}
	
	public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
		
		LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();
		Class<?> targetClass = clazz;

		do {
			LinkedList<InjectionElement> currElements = new LinkedList<InjectionElement>();
		//拿到目标class的所有field字段进行遍历,找到存在注解的field
			for (Field field : targetClass.getDeclaredFields()) {
				Annotation ann = findAutowiredAnnotation(field);
				if (ann != null) {
					//找到存在注解的字段再判断是否静,静态字段不做处理
					if (Modifier.isStatic(field.getModifiers())) {
						
						continue;
					}
					boolean required = determineRequiredStatus(ann);
					currElements.add(new AutowiredFieldElement(field, required,beanFactory));
				}
			}
			
			/**
			 * 下面用于处理方法注入,暂时没有实现
			 */
			/*for (Method method : targetClass.getDeclaredMethods()) {
				//TODO 处理方法注入
			}*/
			
			elements.addAll(0, currElements);
			targetClass = targetClass.getSuperclass();
		}
		while (targetClass != null && targetClass != Object.class);

		return new InjectionMetadata(clazz, elements);
	}
	
	protected boolean determineRequiredStatus(Annotation ann) {
		try {
			Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
			if (method == null) {
				return true;
			}
			return (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, ann));
		}
		catch (Exception ex) {
			return true;
		}
	}
	
	//使用AnnotationUtils来判断一个字段上是否存在指定注解
	private Annotation findAutowiredAnnotation(AccessibleObject ao) {
		for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
			Annotation ann = AnnotationUtils.getAnnotation(ao, type);
			if (ann != null) {
				return ann;
			}
		}
		return null;
	}
	public void setBeanFactory(AutowireCapableBeanFactory beanFactory){
		this.beanFactory = beanFactory;
	}
	
	@Override
	public Object beforeInitialization(Object bean, String beanName) throws BeansException {
		//do nothing
		return bean;
	}
	@Override
	public Object afterInitialization(Object bean, String beanName) throws BeansException {
		// do nothing
		return bean;
	}
	@Override
	public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		return null;
	}
	@Override
	public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
		// do nothing
		return true;
	}

	//自动调用inject(bean)方法
	@Override
	public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {		
		InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
		try {
			metadata.inject(bean);
		}
		catch (Throwable ex) {
			throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
		}		
	}
}
