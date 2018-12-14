package org.litespring.context;
import org.litespring.beans.factory.BeanFactory;
/**
 *	SpringIoc的容器
 *	继承了BeanFactory,获得其getBean方法
 */
public interface ApplicationContext extends BeanFactory{
	
}
