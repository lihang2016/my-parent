package com.my.biz.common.interactive;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 20:53 2017/7/6
 */
public class CPResponse{

    private Boolean success;
    private String message;
    private Integer code;

    public CPResponse(){
        this.success=true;
        this.message="";
        this.code=100;
    }


    /**
     * 把当前对象转换为ViewInfo对象
     */
    /**
     * 把当前对象转换为ViewInfo对象
     */
    public ViewInfo to() {
        return ViewInfo.from(this);
    }

    public static CPResponse create(){
        return new CPResponse();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
