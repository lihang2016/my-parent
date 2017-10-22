package com.my.biz.common.interactive;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by 96230 on 2017/5/29.
 */
@Getter
@Setter
public class SingleRequest<T> {
    @NotNull
    @Valid
    private T data;

    public SingleRequest(){

    }
    public SingleRequest(T data){
        this.data=data;
    }
    public static <T> SingleRequest<T> from(T t) {
        return new SingleRequest<>(t);
    }
}
