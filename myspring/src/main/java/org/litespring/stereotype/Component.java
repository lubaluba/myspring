package org.litespring.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 	@author a3325
 *	Componet注解实现,很久没写过自定义注解了,复习一下
 *	Target:注解使用的地方,这里是类,接口,方法甚至enum都可以使用
 *	Retention:注解生命周期,这里选择保留到运行期,也是最长的一个生命周期
 *	Documented:注解是否包含在javaDoc中
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

	String value() default "";
}
