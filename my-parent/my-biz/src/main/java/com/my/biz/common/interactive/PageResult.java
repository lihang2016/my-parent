package com.my.biz.common.interactive;

import com.my.common.utils.BeanCopier;
import org.springframework.data.domain.Page;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 20:33 2017/12/11
 */
public class PageResult<T> extends CPResponse {
    private Page<T> data;

    public static <T> PageResult<T> from(Page<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setData(page);
        result.setSuccess(Boolean.TRUE);
        return result;
    }
    public static <T, S> PageResult<S> from(Page<T> page, Class<S> clazz,String ...ignoreProperties) {
        PageResult<S> result = new PageResult<>();
        if (page != null) {
            result.setData(page.map(source -> BeanCopier.copy(source, clazz, BeanCopier.CopyStrategy.IGNORE_NULL,ignoreProperties)));
        }
        result.setSuccess(Boolean.TRUE);
        return result;
    }
    public Page<T> getData(){
        return this.data;
    }
    public void setData(Page<T> data) {
        this.data = data;
    }

    public ViewInfo to() {
        return super.to().data(this.data);
    }
}
