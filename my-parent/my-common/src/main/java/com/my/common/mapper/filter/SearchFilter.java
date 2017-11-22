package com.my.common.mapper.filter;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.my.common.mapper.filter.SearchFilter.Operator.NOTNULL;
import static com.my.common.mapper.filter.SearchFilter.Operator.NULL;

public class SearchFilter {
	
	public String fieldName;
	public Object value;
	public Operator operator;
	
	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}
	
	public static List<SearchFilter> parse(Map<String, Object> searchParams) {
		List<SearchFilter> filters = Lists.newArrayList();
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			SearchFilter searchFilter = parse(entry.getKey(), entry.getValue());
			if (searchFilter != null) {
				filters.add(searchFilter);
			}
		}
		return filters;
	}
	
	public static SearchFilter parse(String param, Object value) {
		Assert.hasText(param);
		String[] names = StringUtils.split(param, "_");
		if (names.length != 2) {
			throw new IllegalArgumentException(param + " is not a valid search filter name");
		}
		Operator op = Operator.valueOf(names[0].toUpperCase());
		if (value == null || isBlank(value.toString())) {
			if (!(op == NULL || op == NOTNULL)) {
				return null;
			}
		}
		return new SearchFilter(names[1], op, value);
	}

	private static boolean isBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}
	
	public enum Operator {
							/**
							 * 等于
							 */
							EQ,
							/**
							 * 不等于
							 */
							NEQ,
							/**
							 * like %v%
							 */
							LIKE,
							/**
							 * like %v
							 */
							LLIKE,
							/**
							 * like v%
							 */
							RLIKE,
							/**
							 * 大于
							 */
							GT,
							/**
							 * 小于
							 */
							LT,
							/**
							 * 大于等于
							 */
							GTE,
							/**
							 * 小于等于
							 */
							LTE,
							/**
							 * 包含
							 */
							IN,
							/**
							 * 不包含
							 */
							NOTIN,
							/**
							 * 为空
							 */
							NULL,
							/**
							 * 不为空
							 */
							NOTNULL,;
							public String field(String field){
								return this.name()+"_"+field;
							}

	}
}
