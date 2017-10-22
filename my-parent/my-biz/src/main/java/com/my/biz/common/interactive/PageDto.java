package com.my.biz.common.interactive;

import com.alibaba.fastjson.annotation.JSONField;
import com.my.biz.common.entity.Pages;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 *分页参数dto
 */
public class PageDto extends BaseDto {

	/**
	 * 页码，从0开始
	 */
	@JSONField(serialize = false)
	private Integer pageNo;

	@JSONField(serialize = false)
	private int sizePerPage = Pages.DEFAULT_PAGE_SIZE;

	@JSONField(serialize = false)
	private Sort.Direction sortDirection = Sort.Direction.ASC;

	@JSONField(serialize = false)
	private String[] sortFields;
	@JSONField(serialize = false)
	private List<Sort.Order> orders;
	public PageRequest toPage() {
		if(orders!=null&&!orders.isEmpty()){
			return Pages.newPageRequest(pageNo,sizePerPage,new Sort(orders));
		}
		if (sortFields == null) {
			return Pages.newPageRequest(pageNo, sizePerPage);
		} else {
			return Pages.newPageRequest(pageNo, sizePerPage, sortDirection, sortFields);
		}
	}

	public List<Sort.Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Sort.Order> orders) {
		this.orders = orders;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo - 1;
	}

	public int getSizePerPage() {
		return sizePerPage;
	}

	public void setSizePerPage(int sizePerPage) {
		this.sizePerPage = sizePerPage;
	}

	public Sort.Direction getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(Sort.Direction sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String[] getSortFields() {
		return sortFields;
	}

	public void setSortFields(String[] sortFields) {
		this.sortFields = sortFields;
	}
}
