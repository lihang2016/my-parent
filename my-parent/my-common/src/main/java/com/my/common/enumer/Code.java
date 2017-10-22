package com.my.common.enumer;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:返回编码
 * @Date 2017/7/8 14:41
 */
public enum Code {
    SUCCESS("处理成功",100),
    NOTSUCCESS("处理失败",101),
    INTERNALERROR("内部错误",102),
    ACCOUNTERROR("账号未登录",300),
    ACCOUNTORPASSERROR("账户或者密码错误",444),
    ACCOUNTLOCK("账号已被锁定",405);

    private String message;

    private Integer code;

     Code(String message,Integer code){
        this.message=message;
        this.code=code;
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
