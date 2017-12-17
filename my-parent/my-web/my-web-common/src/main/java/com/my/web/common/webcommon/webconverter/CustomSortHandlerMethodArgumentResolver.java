package com.my.web.common.webcommon.webconverter;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 16:52 2017/12/11
 */
public class CustomSortHandlerMethodArgumentResolver extends SortHandlerMethodArgumentResolver {
    /**
     * 解决前端不能传递这种格式[sort=id,asc&sort=name,asc]的排序参数问题
     * 旧排序格式传参格式(spring支持的): sort=id,asc&sort=name,asc 新排序传参格式:sort=id,asc;name,asc
     * @return
     */
    @Override
    public Sort resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String sortValue = webRequest.getParameter("sort");
        if (sortValue==null || sortValue.length()==0) {
            return null;
        }
        String directionParameter[] = sortValue.split(";");

        return parseParameterIntoSort(directionParameter, ",");
    }

    Sort parseParameterIntoSort(String[] source, String delimiter) {

        List<Sort.Order> allOrders = new ArrayList<Sort.Order>();

        for (String part : source) {

            if (part == null) {
                continue;
            }

            String[] elements = part.split(delimiter);
            Sort.Direction direction = elements.length == 0 ? null : Sort.Direction.fromStringOrNull(elements[elements.length - 1]);

            for (int i = 0; i < elements.length; i++) {

                if (i == elements.length - 1 && direction != null) {
                    continue;
                }

                String property = elements[i];

                if (!StringUtils.hasText(property)) {
                    continue;
                }

                allOrders.add(new Sort.Order(direction, property));
            }
        }
        return allOrders.isEmpty() ? null : new Sort(allOrders);
    }
}
