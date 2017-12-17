
package com.my.common.utils;

import com.my.common.exception.Exceptions;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BeanCopier {
	
	private static final Logger logger = LoggerFactory.getLogger(BeanCopier.class);
	
	private static final String packageName = getPackageName(BeanCopier.class);
	private static final Map<Key, Copy> copierMap = new ConcurrentHashMap<Key, Copy>();
	public static String dumpClass = null;
	public static boolean logSource = false;
	
	public static <T> T copy(Object from, Class<T> toClass, String... ignorePropeties) {
		return copy(from, toClass, CopyStrategy.CONTAIN_NULL, ignorePropeties);
	}
	
	public static <T> T copy(Object from, Class<T> toClass, CopyStrategy strategy, String... ignorePropeties) {
		notNull(toClass, "toClass不能为空");
		try {
			T target = toClass.newInstance();
			copy(from, target, strategy, NoMatchingRule.IGNORE, ignorePropeties);
			return target;
		} catch (Exception e) {
			throw Exceptions.runtimeException(e);
		}
	}
	
	public static void copy(Object from, Object to, String... ignorePropeties) {
		copy(from, to, CopyStrategy.CONTAIN_NULL, NoMatchingRule.IGNORE, ignorePropeties);
	}
	
	public static void copy(Object from, Object to, CopyStrategy strategy, NoMatchingRule noMatchingRule,
							String... ignorePropeties) {
		Key key = getKey(notNull(from, "源对象不能为空"), notNull(to, "目标对象不能为空"), notNull(strategy, "拷贝策略不能为空"),
			ignorePropeties);
		Copy copy = copierMap.get(key);
		if (copy == null) {
			synchronized (BeanCopier.class) {
				copy = copierMap.get(key);
				if (copy == null) {
					BeanCopier.Generator generator = new BeanCopier.Generator();
					generator.setSource(from.getClass());
					generator.setTarget(to.getClass());
					generator.setIgnorePropeties(ignorePropeties);
					generator.setNoMatchingRule(noMatchingRule);
					generator.setStrategy(strategy);
					try {
						copy = generator.generate().newInstance();
						copierMap.put(key, copy);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		copy.copy(from, to);
	}
	
	private static Key getKey(Object from, Object to, CopyStrategy strategy, String[] ignoreProperties) {
		Class<?> fromClass = from.getClass();
		Class<?> toClass = to.getClass();
		return new Key(fromClass, toClass, ignoreProperties);
	}
	
	private static String getPackageName(Class<?> clazz) {
		String className = clazz.getName();
		int lastDotIndex = className.lastIndexOf(".");
		return (lastDotIndex != -1 ? className.substring(0, lastDotIndex) : "");
	}
	
	private static <T> T notNull(T obj, String message) {
		if (obj == null) {
			throw new NullPointerException(message);
		}
		return obj;
	}
	
	private static <T> T notNull(T obj) {
		return notNull(obj, null);
	}
	
	public static enum CopyStrategy {

										IGNORE_NULL,

										CONTAIN_NULL
	}
	
	public static enum NoMatchingRule {

										IGNORE,

										EXCEPTION;

	}
	
	public static interface Copy {
		void copy(Object source, Object target);
	}
	
	private static class Generator {
		private final static String SOURCE = "s";
		private final static String TARGET = "t";
		private static AtomicInteger classNameIndex = new AtomicInteger(1000);
		private Class<?> source;
		private Class<?> target;
		private String[] ignorePropeties = {};
		
		private NoMatchingRule noMatchingRule;
		private CopyStrategy strategy;
		private String beginSource;
		private List<String> propSources = new ArrayList<String>();
		private String endSources;
		
		public static boolean isWrapClass(Class<?> clazz) {
			try {
				return ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
			} catch (Exception e) {
				return false;
			}
		}
		
		public static boolean isWrapClass(Class<?> source, Class<?> target) {
			if (!target.isPrimitive()) {
				return false;
			}
			try {
				return source.getField("TYPE").get(null) == target;
			} catch (Exception e) {
				return false;
			}
		}
		
		public void setStrategy(CopyStrategy strategy) {
			this.strategy = strategy;
		}
		
		public void setSource(Class<?> source) {
			this.source = source;
		}
		
		public void setTarget(Class<?> target) {
			this.target = target;
		}
		
		public void setIgnorePropeties(String[] ignorePropeties) {
			this.ignorePropeties = ignorePropeties;
		}
		
		public void setNoMatchingRule(NoMatchingRule noMatchingRule) {
			this.noMatchingRule = noMatchingRule;
		}
		
		private void generateBegin() {
			// 生成方法签名public void copy(TestBean s, TestBean1 t) {
			beginSource = "public void copy(Object " + SOURCE + "1 ,Object " + TARGET + "1){\n";
			// 强制转换源对象
			String convertSource = source.getName()	+ " " + SOURCE + " =" + "(" + source.getName() + ")" + SOURCE
									+ "1;\n";
			// 强制转换目标对象
			String convertTarget = target.getName()	+ " " + TARGET + " =" + "(" + target.getName() + ")" + TARGET
									+ "1;\n";
			beginSource += convertSource + convertTarget;

		}
		
		private void generateEnd() {
			endSources = "\n}";
		}
		
		private void generateBody() {

			PropertyDescriptor[] getters = getPropertyDescriptors(source);
			PropertyDescriptor[] setters = getPropertyDescriptors(target);

			Map<String, PropertyDescriptor> getterMap = new HashMap<String, PropertyDescriptor>();
			for (PropertyDescriptor getter : getters) {
				getterMap.put(getter.getName(), getter);
			}
			for (PropertyDescriptor setter : setters) {
				PropertyDescriptor getter = getterMap.get(setter.getName());
				if (!checkCanGenSource(setter, getter)) {
					continue;
				}
				Method readMethod = getter.getReadMethod();
				Method writeMethod = setter.getWriteMethod();
				String readMethodName = readMethod.getName();
				String writerMethodName = writeMethod.getName();
				Class<?> getterPropertyType = getter.getPropertyType();
				if (compatible(getter, setter)) {
					if (strategy == CopyStrategy.IGNORE_NULL && !getterPropertyType.isPrimitive()) {
						String source = genCheckWrapperIsNotNullSource(readMethod.getName());
						source += "\t" + genPropertySource(writerMethodName, SOURCE + "." + readMethodName + "()");
						propSources.add(source);
					} else {
						propSources.add(genPropertySource(writerMethodName, SOURCE + "." + readMethodName + "()"));
					}
				} else {
					// 是否是包装类转换
					Class<?> setterPropertyType = setter.getPropertyType();
					if (compatibleWrapper(getter, setter)) {
						Convertor convert = new Convertor(setterPropertyType, SOURCE, readMethod.getName());
						String f = convert.convert();
						if (f != null) {
							if (isWrapClass(getterPropertyType)) {
								String source = genCheckWrapperIsNotNullSource(readMethod.getName());
								source += "\t" + genPropertySource(writerMethodName, f);
								propSources.add(source);
							} else {
								propSources.add(genPropertySource(writerMethodName, f));
							}
							continue;
						}
					} else {
						String typeName = buildTypeName(setter, false);
						String s = TARGET + "." + writerMethodName + "(" + (setterPropertyType.isPrimitive()
							? PrimitiveUtils.class.getName()
									+ ".value((" + PrimitiveUtils.getWrapperClass(setterPropertyType.getName()).getName()
								+ ") " + ConversionServices.class.getName() + ".convert("
								+ (getterPropertyType.isPrimitive()
									? PrimitiveUtils.class.getName() + ".value(" + SOURCE + "." + readMethodName + "())"
									: buildTypeName(getter, false)	+ ".class.cast(" + SOURCE + "." + readMethodName
										+ "())")
								+ ", " + typeName + ".class)));\n"
							: "("	+ typeName + ") " + typeName + ".class.cast( " + ConversionServices.class.getName()
								+ ".convert("
								+ (getterPropertyType.isPrimitive()
									? PrimitiveUtils.class.getName() + ".value(" + SOURCE + "." + readMethodName + "())"
									: buildTypeName(getter, false)	+ ".class.cast(" + SOURCE + "." + readMethodName
										+ "())")
								+ ", " + typeName + ".class)));\n");
						StringBuilder sb = new StringBuilder(400);
						switch (this.noMatchingRule) {
							case IGNORE:
								sb.append("try {\n");
								sb.append(s);
								sb.append("} catch (java.lang.ClassCastException e) {\n");
								sb.append("\n");
								sb.append("}\n");
								break;
							case EXCEPTION:
								sb.append(s);
								break;
							default:
								throw new InternalError();
						}
						this.propSources.add(sb.toString());
						continue;
					}
					warnCantConvert(setter, getter);
				}
			}
		}
		
		private String buildTypeName(PropertyDescriptor setter, boolean isPrimitive) {
			String typeName;
			if (setter.getPropertyType().isArray()) {
				int dn = countDimension(setter.getPropertyType());
				typeName = setter.getPropertyType().getComponentType().getName();
				for (int i = 0; i < dn; i++) {
					typeName += "[]";
				}
			} else {
				typeName = setter.getPropertyType().getName();
			}
			return typeName;
		}
		
		private int countDimension(Class<?> arrayClass) {
			int c = 0;
			for (Class<?> comp = arrayClass; comp != null && comp.isArray(); comp = comp.getComponentType()) {
				c++;
			}
			return c;
		}
		
		private String genCheckWrapperIsNotNullSource(String readName) {
			return "if(" + SOURCE + "." + readName + "()!=null)\n";
		}
		
		private String genPropertySource(String writerMethodName, String getterSource) {
			return TARGET + "." + writerMethodName + "(" + getterSource + ");\n";
		}
		
		private void warnCantConvert(PropertyDescriptor setter, PropertyDescriptor getter) {
			logger.debug("[对象属性复制]属性类型转换失败{}.{}({})->{}.{}({})",
				getter.getReadMethod().getDeclaringClass().getSimpleName(), getter.getName(), getter.getPropertyType(),
				setter.getWriteMethod().getDeclaringClass().getSimpleName(), setter.getName(),
				setter.getPropertyType());
		}
		
		private boolean checkCanGenSource(PropertyDescriptor setter, PropertyDescriptor getter) {
			// 是否被忽略
			if (ignorePropeties != null && isIgnoredProperty(setter)) {
				return false;
			}
			// 检查getter是否存在
			if (getter == null) {
				logger.debug("[对象属性复制]原对象[{}.{}]getter方法不存在", source.getCanonicalName(), setter.getName());
				return false;
			}
			// 检查getter的读方法是否存在
			if (getter.getReadMethod() == null) {
				logger.debug("[对象属性复制]原对象[{}.{}]getter方法不存在", source.getCanonicalName(), getter.getName());
				return false;
			}
			// 检查setter的写方法是否存在
			if (setter.getWriteMethod() == null) {
				logger.debug("[对象属性复制]目标对象[{}.{}]setter方法不存在", target.getCanonicalName(), setter.getName());
				return false;
			}
			return true;
		}
		
		private boolean compatibleWrapper(PropertyDescriptor getter, PropertyDescriptor setter) {

			return isWrapClass(getter.getPropertyType(), setter.getPropertyType())
					|| isWrapClass(setter.getPropertyType(), getter.getPropertyType());
		}
		
		private boolean isIgnoredProperty(PropertyDescriptor descriptor) {
			String propertyName = descriptor.getName();
			if (ArrayUtils.isEmpty(this.ignorePropeties)) {
				return false;
			}
			for (String ignorePropety : ignorePropeties) {
				if (ignorePropety.equals(propertyName)) {
					return true;
				}
			}
			return false;
		}
		
		@SuppressWarnings({ "unchecked" })
		public Class<Copy> generate() {
			generateBegin();
			generateBody();
			generateEnd();
			StringBuilder sb = new StringBuilder();
			sb.append(beginSource);
			for (String propSource : propSources) {
				sb.append(propSource);
			}
			sb.append(endSources);
			String source = sb.toString();
			if (logSource) {
				logger.info("\n{}", source);
			}
			ClassPool pool = ClassPool.getDefault();
			ClassClassPath classPath = new ClassClassPath(this.getClass());
			pool.insertClassPath(classPath);
			CtClass cc = pool.makeClass(packageName + ".CopierImpl" + classNameIndex.incrementAndGet());

			Class<Copy> copyClass = null;
			try {
				cc.addInterface(pool.get(Copy.class.getName()));
				CtMethod m = CtNewMethod.make(source, cc);
				cc.addMethod(m);
				if (dumpClass != null) {
					CtClass.debugDump = dumpClass;
				}
				ClassLoader classLoader = getDefaultClassLoader();
				logger.debug("classloader:{}", classLoader);
				copyClass = cc.toClass(classLoader, null);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return copyClass;
		}
		
		private boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter) {
			return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
		}
		
		private ClassLoader getDefaultClassLoader() {
			return org.springframework.util.ClassUtils.getDefaultClassLoader();
		}
		
		public PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
			BeanInfo beanInfo;
			try {
				beanInfo = Introspector.getBeanInfo(clazz);
				return beanInfo.getPropertyDescriptors();
			} catch (IntrospectionException e) {
				throw new RuntimeException(e);
			}

		}
		
	}
	
	private static class Convertor {
		private String sourceName;
		private String readMethodName;
		private Class<?> targetType;
		
		private Convertor(Class<?> targetType, String sourceName, String readMethodName) {
			this.targetType = notNull(targetType);
			this.sourceName = notNull(sourceName);
			this.readMethodName = notNull(readMethodName);
		}
		
		public String convert() {
			if (targetType.isPrimitive()) {
				String f = getPrimitiveFormat();
				return getterSource() + "." + f + "()";
			} else if (Generator.isWrapClass(targetType)) {
				String f = getWrapperFormat();
				return f + "(" + getterSource() + ")";
			} else {
				return null;
			}
		}
		
		private String getterSource() {
			return sourceName + "." + readMethodName + "()";
		}
		
		private String getPrimitiveFormat() {
			return targetType.getName() + "Value";
		}
		
		private String getWrapperFormat() {
			return targetType.getSimpleName() + ".valueOf";
		}
		
	}
	
	private static class Key {
		private Class<?> fromClass;
		private Class<?> toClass;
		private String[] ignoreProperties;
		private CopyStrategy strategy;
		
		@SuppressWarnings("unused")
		public Key(Class<?> fromClass, Class<?> toClass) {
			this.fromClass = fromClass;
			this.toClass = toClass;
		}
		
		public Key(Class<?> fromClass, Class<?> toClass, String[] ignoreProperties) {
			super();
			this.fromClass = fromClass;
			this.toClass = toClass;
			this.ignoreProperties = ignoreProperties;
		}
		
		@SuppressWarnings("unused")
		public Key(Class<?> fromClass, Class<?> toClass, CopyStrategy strategy, String[] ignoreProperties) {
			super();
			this.fromClass = fromClass;
			this.toClass = toClass;
			this.ignoreProperties = ignoreProperties;
			this.strategy = strategy;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			
			Key key = (Key) o;
			
			if (fromClass != null ? !fromClass.equals(key.fromClass) : key.fromClass != null)
				return false;
			if (!Arrays.equals(ignoreProperties, key.ignoreProperties))
				return false;
			if (strategy != key.strategy)
				return false;
			if (toClass != null ? !toClass.equals(key.toClass) : key.toClass != null)
				return false;
			
			return true;
		}
		
		@Override
		public int hashCode() {
			int result = fromClass != null ? fromClass.hashCode() : 0;
			result = 31 * result + (toClass != null ? toClass.hashCode() : 0);
			result = 31 * result + (ignoreProperties != null ? Arrays.hashCode(ignoreProperties) : 0);
			result = 31 * result + (strategy != null ? strategy.hashCode() : 0);
			return result;
		}
		
	}
}