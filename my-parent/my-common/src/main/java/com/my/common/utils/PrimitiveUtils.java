
package com.my.common.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class PrimitiveUtils {
	
	public static final int[] EMPTY_INTS = new int[0];
	public static final long[] EMPTY_LONGS = new long[0];
	public static final short[] EMPTY_SHORTS = new short[0];
	public static final float[] EMPTY_FLOATS = new float[0];
	public static final double[] EMPTY_DOUBLES = new double[0];
	public static final byte[] EMPTY_BYTES = new byte[0];
	public static final char[] EMPTY_CHARS = new char[0];
	public static final boolean[] EMPTY_BOOLEANS = new boolean[0];
	public static final Integer[] EMPTY_INT_WRAPPERS = new Integer[0];
	public static final Long[] EMPTY_LONG_WRAPPERS = new Long[0];
	public static final Short[] EMPTY_SHORT_WRAPPERS = new Short[0];
	public static final Float[] EMPTY_FLOAT_WRAPPERS = new Float[0];
	public static final Double[] EMPTY_DOUBLE_WRAPPERS = new Double[0];
	public static final Byte[] EMPTY_BYTE_WRAPPERS = new Byte[0];
	public static final Character[] EMPTY_CHAR_WRAPPERS = new Character[0];
	public static final Boolean[] EMPTY_BOOLEAN_WRAPPERS = new Boolean[0];
	private static final Map<String, TypeHolder> PRIMITIVES = new HashMap<String, TypeHolder>();
	
	static {
		PRIMITIVES.put(Byte.TYPE.getName(), new ByteTypeHolder());
		PRIMITIVES.put(Character.TYPE.getName(), new CharTypeHolder());
		PRIMITIVES.put(Short.TYPE.getName(), new ShortTypeHolder());
		PRIMITIVES.put(Integer.TYPE.getName(), new IntTypeHolder());
		PRIMITIVES.put(Long.TYPE.getName(), new LongTypeHolder());
		PRIMITIVES.put(Float.TYPE.getName(), new FloatTypeHolder());
		PRIMITIVES.put(Double.TYPE.getName(), new DoubleTypeHolder());
		PRIMITIVES.put(Boolean.TYPE.getName(), new BooleanTypeHolder());
	}
	
	public static short value(Short value) {
		if (value == null) {
			return (short) 0;
		}
		return value.shortValue();
	}
	
	public static int value(Integer value) {
		if (value == null) {
			return 0;
		}
		return value.intValue();
	}
	
	public static long value(Long value) {
		if (value == null) {
			return 0l;
		}
		return value.longValue();
	}
	
	public static float value(Float value) {
		if (value == null) {
			return 0.0f;
		}
		return value.floatValue();
	}
	
	public static double value(Double value) {
		if (value == null) {
			return 0.0d;
		}
		return value.doubleValue();
	}
	
	public static byte value(Byte value) {
		if (value == null) {
			return (byte) 0;
		}
		return value.byteValue();
	}
	
	public static boolean value(Boolean value) {
		if (value == null) {
			return false;
		}
		return value.booleanValue();
	}
	
	public static char value(Character value) {
		if (value == null) {
			return '\u0000';
		}
		return value.charValue();
	}
	
	public static Short value(short value) {
		return Short.valueOf(value);
	}
	
	public static Integer value(int value) {
		return Integer.valueOf(value);
	}
	
	public static Long value(long value) {
		return Long.valueOf(value);
	}
	
	public static Float value(float value) {
		return Float.valueOf(value);
	}
	
	public static Double value(double value) {
		return Double.valueOf(value);
	}
	
	public static Byte value(byte value) {
		return Byte.valueOf(value);
	}
	
	public static Boolean value(boolean value) {
		return Boolean.valueOf(value);
	}
	
	public static Character value(char value) {
		return Character.valueOf(value);
	}
	
	public static int[] values(Integer[] values) {
		if (values == null) {
			return EMPTY_INTS;
		}
		int[] intValues = new int[values.length];
		for (int i = 0; i < values.length; i++) {
			intValues[i] = value(values[i]);
		}
		return intValues;
	}
	
	public static long[] values(Long[] values) {
		if (values == null) {
			return EMPTY_LONGS;
		}
		long[] longValues = new long[values.length];
		for (int i = 0; i < values.length; i++) {
			longValues[i] = value(values[i]);
		}
		return longValues;
	}
	
	public static short[] values(Short[] values) {
		if (values == null) {
			return EMPTY_SHORTS;
		}
		short[] shortValues = new short[values.length];
		for (int i = 0; i < values.length; i++) {
			shortValues[i] = value(values[i]);
		}
		return shortValues;
	}
	
	public static float[] values(Float[] values) {
		if (values == null) {
			return EMPTY_FLOATS;
		}
		float[] floatValues = new float[values.length];
		for (int i = 0; i < values.length; i++) {
			floatValues[i] = value(values[i]);
		}
		return floatValues;
	}
	
	public static double[] values(Double[] values) {
		if (values == null) {
			return EMPTY_DOUBLES;
		}
		double[] doubleValues = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			doubleValues[i] = value(values[i]);
		}
		return doubleValues;
	}
	
	public static boolean[] values(Boolean[] values) {
		if (values == null) {
			return EMPTY_BOOLEANS;
		}
		boolean[] booleanValues = new boolean[values.length];
		for (int i = 0; i < values.length; i++) {
			booleanValues[i] = value(values[i]);
		}
		return booleanValues;
	}
	
	public static byte[] values(Byte[] values) {
		if (values == null) {
			return EMPTY_BYTES;
		}
		byte[] byteValues = new byte[values.length];
		for (int i = 0; i < values.length; i++) {
			byteValues[i] = value(values[i]);
		}
		return byteValues;
	}
	
	public static char[] values(Character[] values) {
		if (values == null) {
			return EMPTY_CHARS;
		}
		char[] charValues = new char[values.length];
		for (int i = 0; i < values.length; i++) {
			charValues[i] = value(values[i]);
		}
		return charValues;
	}
	
	public static Integer[] values(int[] values) {
		if (values == null) {
			return EMPTY_INT_WRAPPERS;
		}
		Integer[] integerValues = new Integer[values.length];
		for (int i = 0; i < values.length; i++) {
			integerValues[i] = Integer.valueOf(values[i]);
		}
		return integerValues;
	}
	
	public static Long[] values(long[] values) {
		if (values == null) {
			return EMPTY_LONG_WRAPPERS;
		}
		Long[] longValues = new Long[values.length];
		for (int i = 0; i < values.length; i++) {
			longValues[i] = Long.valueOf(values[i]);
		}
		return longValues;
	}
	
	public static Short[] values(short[] values) {
		if (values == null) {
			return EMPTY_SHORT_WRAPPERS;
		}
		Short[] shortValues = new Short[values.length];
		for (int i = 0; i < values.length; i++) {
			shortValues[i] = Short.valueOf(values[i]);
		}
		return shortValues;
	}
	
	public static Float[] values(float[] values) {
		if (values == null) {
			return EMPTY_FLOAT_WRAPPERS;
		}
		Float[] floatValues = new Float[values.length];
		for (int i = 0; i < values.length; i++) {
			floatValues[i] = Float.valueOf(values[i]);
		}
		return floatValues;
	}
	
	public static Double[] values(double[] values) {
		if (values == null) {
			return EMPTY_DOUBLE_WRAPPERS;
		}
		Double[] doubleValues = new Double[values.length];
		for (int i = 0; i < values.length; i++) {
			doubleValues[i] = Double.valueOf(values[i]);
		}
		return doubleValues;
	}
	
	public static Boolean[] values(boolean[] values) {
		if (values == null) {
			return EMPTY_BOOLEAN_WRAPPERS;
		}
		Boolean[] booleanValues = new Boolean[values.length];
		for (int i = 0; i < values.length; i++) {
			booleanValues[i] = Boolean.valueOf(values[i]);
		}
		return booleanValues;
	}
	
	public static Byte[] values(byte[] values) {
		if (values == null) {
			return EMPTY_BYTE_WRAPPERS;
		}
		Byte[] byteValues = new Byte[values.length];
		for (int i = 0; i < values.length; i++) {
			byteValues[i] = Byte.valueOf(values[i]);
		}
		return byteValues;
	}
	
	public static Character[] values(char[] values) {
		if (values == null) {
			return EMPTY_CHAR_WRAPPERS;
		}
		Character[] characterValues = new Character[values.length];
		for (int i = 0; i < values.length; i++) {
			characterValues[i] = Character.valueOf(values[i]);
		}
		return characterValues;
	}
	
	public static Class<?> getPrimitiveClass(String primitiveName) {
		TypeHolder typeHolder = PRIMITIVES.get(primitiveName);
		if (typeHolder == null) {
			return null;
		}
		return typeHolder.getType();
	}
	
	public static Class<?> getWrapperClass(String primitiveName) {
		TypeHolder typeHolder = PRIMITIVES.get(primitiveName);
		if (typeHolder == null) {
			return null;
		}
		return typeHolder.getWrapper();
	}
	
	public static Class<?>[] getAllPrimitiveClasses() {
		return new Class<?>[] {	Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE,
								Boolean.TYPE };
	}
	
	public static Class<?>[] getAllPrimitiveArrayClasses() {
		return new Class<?>[] {	byte[].class, char[].class, short[].class, int[].class, long[].class, float[].class,
								double[].class, boolean[].class };
	}
	
	public static Class<?>[] getAllWrapperClasses() {
		return new Class<?>[] {	Byte.class, Character.class, Short.class, Integer.class, Long.class, Float.class,
								Double.class, Boolean.class };
	}
	
	public static Class<?>[] getAllWrapperArrayClasses() {
		return new Class<?>[] {	Byte[].class, Character[].class, Short[].class, Integer[].class, Long[].class,
								Float[].class, Double[].class, Boolean[].class };
	}
	
	private static interface TypeHolder {
		
		Class<?> getType();
		
		Class<?> getWrapper();
		
	}
	
	private static class ByteTypeHolder implements TypeHolder {
		
		public Class<?> getType() {
			return Byte.TYPE;
		}
		
		public Class<?> getWrapper() {
			return Byte.class;
		}
		
	}
	
	private static class CharTypeHolder implements TypeHolder {
		
		public Class<?> getType() {
			return Character.TYPE;
		}
		
		public Class<?> getWrapper() {
			return Character.class;
		}
		
	}
	
	private static class ShortTypeHolder implements TypeHolder {
		
		public Class<?> getType() {
			return Short.TYPE;
		}
		
		public Class<?> getWrapper() {
			return Short.class;
		}
		
	}
	
	private static class IntTypeHolder implements TypeHolder {
		
		public Class<?> getType() {
			return Integer.TYPE;
		}
		
		public Class<?> getWrapper() {
			return Integer.class;
		}
		
	}
	
	private static class LongTypeHolder implements TypeHolder {
		
		public Class<?> getType() {
			return Long.TYPE;
		}
		
		public Class<?> getWrapper() {
			return Long.class;
		}
		
	}
	
	private static class FloatTypeHolder implements TypeHolder {
		
		public Class<?> getType() {
			return Float.TYPE;
		}
		
		public Class<?> getWrapper() {
			return Float.class;
		}
		
	}
	
	private static class DoubleTypeHolder implements TypeHolder {
		
		public Class<?> getType() {
			return Double.TYPE;
		}
		
		public Class<?> getWrapper() {
			return Double.class;
		}
		
	}
	
	private static class BooleanTypeHolder implements TypeHolder {
		
		public Class<?> getType() {
			return Boolean.TYPE;
		}
		
		public Class<?> getWrapper() {
			return Boolean.class;
		}
		
	}
	
}