package com.my.biz.common.interactive;

/**
 * Created by 96230 on 2017/5/29.
 */
public   class SingleResponse<T>  extends CPResponse{
    private T data;

    public SingleResponse(T data){
        this.data=data;
    }
    public SingleResponse(){

    }

    public static <T> SingleResponse<T> from(T data){
        SingleResponse<T> response = new SingleResponse<>();
            if (data == null) {
                response.setCode(999);
                response.setMessage("获取数据失败");
                response.setSuccess(Boolean.FALSE);
            } else {
                response.setData(data);
                response.setCode(100);
                response.setMessage("获取数据成功");
                response.setSuccess(Boolean.TRUE);
            }
        return response;
    }

    //失败的方法
    public SingleResponse failure(String msg,Integer code){
        this.setMessage(msg);
        this.setCode(code);
        this.setSuccess(Boolean.FALSE);
        return this;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
