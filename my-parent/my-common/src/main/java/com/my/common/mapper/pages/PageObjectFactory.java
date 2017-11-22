package com.my.common.mapper.pages;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.springframework.data.domain.Page;

public class PageObjectFactory extends DefaultObjectFactory {
	
	private static final long serialVersionUID = -1082960721558661578L;
	
	@Override
	public <T> T create(Class<T> type) {
		if (type == Page.class) {
			return (T) new ExPageImpl();
		}
		return create(type, null, null);
	}
	
	@Override
	public <T> boolean isCollection(Class<T> type) {
		if (type == Page.class) {
			return true;
		}
		return super.isCollection(type);
	}
	
}
