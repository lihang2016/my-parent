package com.my.common.mapper.pages;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

import java.util.List;

public class ExPageObjectWrapper implements ObjectWrapper {
	
	private ExPageImpl exPage;
	
	public ExPageObjectWrapper(ExPageImpl exPage) {
		super();
		this.exPage = exPage;
	}
	
	@Override
	public Object get(PropertyTokenizer prop) {
		return null;
	}
	
	@Override
	public <E> void addAll(List<E> element) {
		PageList page = (PageList) element;
        ExPageImpl ori=page.getEs();
        exPage.setPageable(ori.getPageable());
        exPage.setContent(ori.getContent());
		exPage.setTotal(ori.getTotalElements());
	}
	
	@Override
	public void set(PropertyTokenizer prop, Object value) {
		
	}
	
	@Override
	public String findProperty(String name, boolean useCamelCaseMapping) {
		return null;
	}
	
	@Override
	public String[] getGetterNames() {
		return new String[0];
	}
	
	@Override
	public String[] getSetterNames() {
		return new String[0];
	}
	
	@Override
	public Class<?> getSetterType(String name) {
		return null;
	}
	
	@Override
	public Class<?> getGetterType(String name) {
		return null;
	}
	
	@Override
	public boolean hasSetter(String name) {
		return false;
	}
	
	@Override
	public boolean hasGetter(String name) {
		return false;
	}
	
	@Override
	public MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory) {
		return null;
	}
	
	@Override
	public boolean isCollection() {
		return false;
	}
	
	@Override
	public void add(Object element) {
		
	}
}