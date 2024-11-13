package com.back.core;

/**
 * 自定义异常类
 */
public class MyException extends RuntimeException {

    private Integer code;

    private Object object;

    public MyException(ResultMassage resultEnums) {
        super(resultEnums.msg());
        this.code = resultEnums.getCode();
    }

    public Object getObject() {
        return object;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
