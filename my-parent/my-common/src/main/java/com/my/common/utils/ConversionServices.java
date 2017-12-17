
package com.my.common.utils;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServices {
	private static DefaultConversionService conversionService = new DefaultConversionService();
	
	public static boolean canConvert(Class<?> sourceType, Class<?> targetType) {
		return conversionService.canConvert(sourceType, targetType);
	}
	
	public static boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
		return conversionService.canConvert(sourceType, targetType);
	}
	
	public static <T> T convert(Object source, Class<T> targetType) {
		return conversionService.convert(source, targetType);
	}
	
	public static Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		return conversionService.convert(source, sourceType, targetType);
	}
}
