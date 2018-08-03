package org.litespring.beans.factory.xml;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.impl.BeanDefinitionRegisty;
import org.litespring.beans.impl.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.exception.BeanDefinitionStoreException;
import org.litespring.utils.StringUtils;
/**
 * 	@author a3325
 *	解析xml文件来获取BeanDefinition对象的类
 *	该类解析xml后依赖BeanDefinitionRegisty接口来注册BeanDefinition对象,并提供给BeanFactory
 */
@SuppressWarnings("unchecked")
public class XmlBeanDefinitionReader {
	
	public static final String ID_ATTRIBUTE = "id";
	
	public static final String CLASS_ATTRIBUTE = "class";
	
	public static final String SCOPE_ATTRUBUTE = "scope";
	
	public static final String REF_ATTRUBUTE = "ref";
	
	public static final String PROPERTY_ELEMENT = "property";
	
	public static final String VALUE_ATTRUBUTE = "value";
	
	public static final String NAME_ATTRUBUTE = "name"; 
	
	public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
	
	public static final String TYPE_ATTRUBUTE = "type";
	
	private BeanDefinitionRegisty registy;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public XmlBeanDefinitionReader(BeanDefinitionRegisty registy) {
		this.registy = registy;
	}
	public void loadBeanDefinitions(Resource r) {
		
		try (InputStream in = r.getInputStream()) {
			
			SAXReader reader = new SAXReader();
			Document doc = reader.read(in);
			Element root  = doc.getRootElement();
			Iterator<Element> iter = root.elementIterator();
			while (iter.hasNext()) {
				Element ele  = iter.next();
				String id = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
				if(ele.attribute(SCOPE_ATTRUBUTE) != null) {
					bd.setScope(ele.attributeValue(SCOPE_ATTRUBUTE));
				}
				//解析构造函数
				parseConstructorArgElements(ele, bd);
				//解析setter注入
				parsePropertyElement(ele, bd);
				
				this.registy.registerBeanDefinition(id, bd);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BeanDefinitionStoreException("IOException paraing XML, document exception");
		}
	}
	//解析<property>创建PropertyValue并设置BeanDefition的list<PropertyValue>
	public void parsePropertyElement(Element beanElement, BeanDefinition bd) {
		Iterator<Element> iterator = beanElement.elementIterator(PROPERTY_ELEMENT);
		while (iterator.hasNext()) {
			Element propEle = iterator.next();
			String propertyName = propEle.attributeValue(NAME_ATTRUBUTE);
			if (!StringUtils.hasLength(propertyName)) {
				logger.fatal("Tag 'property' must hava a 'name' attribute");
				return;
			}
			
			Object val = parPeopertyElement(propEle, bd, propertyName);
			PropertyValue pv = new PropertyValue(propertyName, val);
			bd.getPropertyValues().add(pv);
		}
	}
	
	private Object parPeopertyElement(Element ele, BeanDefinition bd, String propertyName) {
		String elementName = (propertyName != null)?"<property> element for property '" + propertyName + "'":
			"<constructor-arg> element";
		
		boolean hasRefAttribute = (ele.attribute(REF_ATTRUBUTE) != null);
		boolean hasValueAttrubute = (ele.attribute(VALUE_ATTRUBUTE) != null);
		
		if (hasRefAttribute) {
			String refName = ele.attributeValue(REF_ATTRUBUTE);
			if (!StringUtils.hasText(refName)) {
				logger.error(elementName + " contains empty 'ref' attribute");
			}
			RuntimeBeanReference ref = new RuntimeBeanReference(refName);
			return ref;
		} else if (hasValueAttrubute) {
			TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRUBUTE));
			return valueHolder;
		} else {
			throw new RuntimeException(elementName + "must specify a ref or a value");
		}
	}
	
	public void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
		
		Iterator<Element> iter = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
		while (iter.hasNext()) {
			Element ele = iter.next();
			parseConstructorArgElement(ele, bd);
		}
	}
	
	public void parseConstructorArgElement(Element ele, BeanDefinition bd) {
		
		String typeAttr = ele.attributeValue(TYPE_ATTRUBUTE);
		String nameAttr = ele.attributeValue(NAME_ATTRUBUTE);
		Object value = parPeopertyElement(ele, bd, null);
		ConstructorArgument.ValueHolder valueHolder= new ConstructorArgument.ValueHolder(value);
		if (StringUtils.hasLength(typeAttr)) {
			valueHolder.setType(typeAttr);
		}
		if (StringUtils.hasLength(nameAttr)) {
			valueHolder.setName(nameAttr);
		}
		
		bd.getConstructorArgument().addArguentValue(valueHolder);
		
	}
}
