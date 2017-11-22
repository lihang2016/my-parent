package com.my.common.mapper.pages;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

public class PageObjectWrapperFactory extends DefaultObjectWrapperFactory {
	@Override
	public boolean hasWrapperFor(Object object) {
		if (ExPageImpl.class.isAssignableFrom(object.getClass())) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
		if (ExPageImpl.class.isAssignableFrom(object.getClass())) {
			return new ExPageObjectWrapper((ExPageImpl) object);
		}
		throw new ReflectionException(
			"The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
	}
}
