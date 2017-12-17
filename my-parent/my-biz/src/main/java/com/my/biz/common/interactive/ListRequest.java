package com.my.biz.common.interactive;

import com.google.common.collect.Maps;
import com.my.common.utils.Null;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Map;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 17:21 2017/12/11
 */
@Getter
@Setter
public class ListRequest<T>  {
    private Map<String, Object> map;
    private Sort sort;

    public static <T> ListRequest<T> from(T data) {
        ListRequest<T> order = new ListRequest<>();
        return order;
    }
    public static  ListRequest<Null> from() {
        ListRequest<Null> order = new ListRequest<>();
        return order;
    }
    public ListRequest<T> map(String key, Object value) {
        if (this.map == null) {
            this.map = Maps.newHashMap();
        }
        map.put(key, value);
        return this;
    }

    public ListRequest<T> sort(Sort sort) {
        this.sort = sort;
        return this;
    }
}
