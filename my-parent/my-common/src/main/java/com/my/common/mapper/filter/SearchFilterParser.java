
package com.my.common.mapper.filter;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchFilterParser {
	private static final ConversionService conversionService = EnhanceDefaultConversionService.INSTANCE;
	private static final char UNDERLINE = '_';

	public static String parseSqlField(SearchFilter searchFilter, Class proType) {
		StringBuilder sb = new StringBuilder();
		sb.append(" ");
		sb.append(camelToUnderline(searchFilter.fieldName));
		sb.append(" ");
		Object value = convert(searchFilter, proType);
		if (value instanceof Date) {
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = simpleDateFormat.format((Date) value);
		}
		//fixme: sql injection
		switch (searchFilter.operator) {
			case EQ:
				if (value instanceof String) {
					sb.append(" = '" + value + "'");
				} else {
					sb.append(" = " + value);
				}
				break;
			case NEQ:
				if (value instanceof String) {
					sb.append(" != '" + value + "'");
				} else {
					sb.append(" != " + value);
				}
				break;
			case LIKE:
				sb.append(" like '%" + value + "%'");
				break;
			case LLIKE:
				sb.append(" like '%" + value + "'");
				break;
			case RLIKE:
				sb.append(" like '" + value + "%'");
				break;
			case GT:
				if (value instanceof String) {
					sb.append(" > '" + value + "'");
				} else {
					sb.append(" > " + value);
				}
				break;
			case LT:
				if (value instanceof String) {
					sb.append(" < '" + value + "'");
				} else {
					sb.append(" < " + value);
				}
				break;
			case GTE:
				if (value instanceof String) {
					sb.append(" >= '" + value + "'");
				} else {
					sb.append(" >= " + value);
				}
				break;
			case LTE:
				if (value instanceof String) {
					sb.append(" <= '" + value + "'");
				} else {
					sb.append(" <= " + value);
				}
				break;
			case NULL:
				sb.append(" is null ");
				break;
			case NOTNULL:
				sb.append(" is not null ");
				break;
			case IN:
				sb.append(" in (");
				genInClause(proType, sb, (List) value);
				break;
			case NOTIN:
				sb.append(" not in (");
				genInClause(proType, sb, (List) value);
				break;
		}

		return sb.toString();
	}

	private static void genInClause(Class proType, StringBuilder sb, List value) {
		if (Number.class.isAssignableFrom(proType)) {
			StringBuilder tmp = new StringBuilder();
			for (Object o : value) {
				tmp.append(o + ",");
			}
			tmp.deleteCharAt(tmp.length() - 1);
			sb.append(tmp.toString()).append(")");
		} else {
			StringBuilder tmp = new StringBuilder();
			for (Object o : value) {
				tmp.append("'" + o + "',");
			}
			tmp.deleteCharAt(tmp.length() - 1);
			sb.append(tmp.toString()).append(")");
		}
	}

	private static Object convert(SearchFilter searchFilter, Class proType) {
		if (proType.isEnum()&&!isInQuery(searchFilter.operator)) {
			return searchFilter.value;
		}
		if(proType.toString().equals("class com.zds.boot.udc.UDC")){
			return searchFilter.value;
		}
		Object value = null;
		if (isInQuery(searchFilter.operator)) {
			if (searchFilter.value == null) {
				throw new RuntimeException("操作符[" + searchFilter.operator + "]时，值不能为null");
			}
			if (!(Number.class.isAssignableFrom(proType) || String.class.isAssignableFrom(proType)||proType.isEnum())) {
				throw new RuntimeException("操作符[" + searchFilter.operator + "]时，支持属性为String或者数字或者枚举");
			}
			List tmp;
			if (searchFilter.value.getClass().isArray()) {
				tmp = CollectionUtils.arrayToList(searchFilter.value);
			} else if (List.class.isAssignableFrom(searchFilter.value.getClass())) {
				tmp = (List) searchFilter.value;
			} else if (String.class.isAssignableFrom(searchFilter.value.getClass())) {
				tmp = Lists.newArrayList(Splitter.on(",").trimResults().splitToList((String) searchFilter.value));
			} else {
				throw new RuntimeException(
					"操作符[" + searchFilter.operator + "]时，支持参数类型为数组，list，String，value=" + searchFilter.value);
			}
			if (tmp.isEmpty()) {
				throw new RuntimeException("操作符[" + searchFilter.operator + "]时，集合不能为空，value=" + searchFilter.value);
			}
			List result = Lists.newArrayList();
			for (int i = 0; i < tmp.size(); i++) {
				Object cur = tmp.get(i);
				if (!proType.isAssignableFrom(cur.getClass())) {
					result.add(conversionService.convert(cur, proType));
				} else {
					result.add(cur);
				}
			}
			value = result;
		}else if (searchFilter.operator == SearchFilter.Operator.LIKE
				||searchFilter.operator == SearchFilter.Operator.LLIKE
				||searchFilter.operator == SearchFilter.Operator.LLIKE){
			//判断like 查询时输入% _的情况
			value=((String) searchFilter.value).replaceAll("\\%", "\\\\%").replaceAll("\\_", "\\\\_").trim();
		} else{
			if (searchFilter.value != null) {
				if (!proType.isAssignableFrom(searchFilter.value.getClass())) {
					if (searchFilter.operator == SearchFilter.Operator.LTE
						&& proType.isAssignableFrom(java.sql.Date.class)) {
						String oriValue = (String) searchFilter.value;
						if (oriValue.length() =="yyyy-MM-dd".length()) {
							searchFilter.value = addDay(Dates.parse(oriValue),1);
						}
					}
					value = conversionService.convert(searchFilter.value, proType);
				} else {
					value = searchFilter.value;
				}

			} else {
				if (!(searchFilter.operator == SearchFilter.Operator.NULL
						|| searchFilter.operator == SearchFilter.Operator.NOTNULL)) {
					throw new RuntimeException("操作符[" + searchFilter.operator + "]时，值不能为null");
				}
			}
		}

		return value;
	}

	/**
	 * 判断操作符是in还是notin
	 * @param operator
	 * @return
	 */
	 private static boolean isInQuery(SearchFilter.Operator operator){
		return (operator == SearchFilter.Operator.IN ||operator== SearchFilter.Operator.NOTIN);

	 }
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private static Date addDay(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime();
	}
}
