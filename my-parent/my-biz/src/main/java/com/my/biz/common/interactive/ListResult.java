package com.my.biz.common.interactive;

import com.google.common.collect.Lists;
import com.my.common.utils.BeanCopier;

import java.util.Collections;
import java.util.List;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 17:44 2017/12/11
 */
public class ListResult<T> extends CPResponse {
    private List<T> data;

    public static <T> ListResult<T> from(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setData(list);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T, S> ListResult<S> from(List<T> list, Class<S> clazz,String ...ignoreProperties) {
        ListResult<S> result = new ListResult<>();
        if (list != null && !list.isEmpty()) {
            List<S> sList = Lists.newArrayListWithCapacity(list.size());
            for (T t : list) {
                sList.add(BeanCopier.copy(t, clazz, BeanCopier.CopyStrategy.IGNORE_NULL,ignoreProperties));
            }
            result.setData(sList);
        } else {
            result.setData(Collections.emptyList());
        }
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public List<T> getData(){
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public ViewInfo to() {
        return super.to().data(data);
    }
}
