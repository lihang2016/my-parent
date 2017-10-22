package com.my.biz.common.interactive;

import com.my.biz.common.entity.Pages;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 96230 on 2017/5/29.
 */
public class ViewInfo implements Serializable {
    private static final long serialVersionUID = -4366897250108397918L;
    /**
     * 数据
     */
    private Object data;

    /**
     * 处理结果
     */
    private boolean success;

    /**
     * 状态码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ViewInfo from(CPResponse cpResponse){
        ViewInfo viewResultInfo = new ViewInfo();
        viewResultInfo.setMessage(cpResponse.getMessage());
        viewResultInfo.setSuccess(cpResponse.getSuccess());
        viewResultInfo.setCode(cpResponse.getCode().toString());
        if(cpResponse instanceof SingleResponse){
            SingleResponse singleResponse= (SingleResponse) cpResponse;
            if(singleResponse.getData() instanceof PageImpl){
                PageImpl page = (PageImpl) singleResponse.getData();
                viewResultInfo.setData(new CPPageImpl(page));
            }else {
                viewResultInfo.setData(singleResponse.getData());
            }
        }
        return viewResultInfo;
    }
    /**
     * 用于json序列化
     */
    public static  class CPPageImpl {
        private PageImpl page;

        public CPPageImpl(PageImpl page) {
            this.page = page;
        }

        public List getResult() {
            return page.getContent();
        }

        public long getTotalCount() {
            return page.getTotalElements();
        }

        public int getTotalPageCount() {
            return page.getTotalPages();
        }

        public int getCurrentPageNo() {
            return page.getNumber() + 1;
        }

        public int getPageSize() {
            return Pages.DEFAULT_PAGE_SIZE;
        }
    }
}
