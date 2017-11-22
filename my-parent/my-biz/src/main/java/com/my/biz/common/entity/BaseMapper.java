package com.my.biz.common.entity;


import com.my.common.mapper.*;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:mybatis 通用mapper
 * @Date 2017/10/21 13:33
 */
public interface BaseMapper<T extends AggEntity> extends FindMapper<T>,CreateMapper<T>,FlushMapper<T>,GetMapper<T>,ListMapper<T>,RemoveMapper<T>,SavesMapper<T>,UpdateMapper<T> {

}
