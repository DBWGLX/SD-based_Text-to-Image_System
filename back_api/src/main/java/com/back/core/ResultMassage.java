package com.back.core;

/**
 *  错误提示枚举
 */
public enum ResultMassage {

    INCOMPLETE_INFORMATION(400,"信息不完整"),
    NO_DATA_ERROR(400,"您还没有添加任何信息"),
    DATA_EXIST(400,"您已经添加过该数据"),
    TOKEN_ERROR(414, "身份验证已经过期,请重新登录"),
    BIRTHDAY_IN_FUTURE(400,"您怎么会出生在未来呢"),
    PHONE_ERROR(400, "非法手机号"),
    BUSINESS_LIMIT_CONTROL(400,"请求频繁，请稍后再试"),
    UNKNOWN_ERROR(400,"系统繁忙,请稍后重试!"),
    ACCOUNT_PASSWORD_EMPTY(400,"账号密码或账号不能为空"),
    ACCOUNT_PASSWORD_ERROR(400,"账号或密码错误"),
    ACCOUNT_LOGOUT(400, "账号已经注销"),
    ACCOUNT_THERE_ERROR(400,"账号已经存在"),
    ACCOUNT_NOT_THERE_ERROR(400,"账号不存在"),
    DATA_THERE_ERROR(400,"已经存在该资料"),
    ACCOUNT_IS_NULL(400,"账号不能为空"),
    CODE_ERROR(400,"短信验证码错误"),
    CODE_EXPIRED(400,"短信验证码过期"),
    SEND_SMS_FAIL(400,"发送短信失败"),
    FILE_ERROR(400,"文件上传异常"),
    DATA_NOT_DELETE(400,"该数据已经引用，无法删除"),
    DATA_NOT_FOUND(400,"数据未找到，无法进一步处理"),
    RELEVANCE_ERROR(400,"数据关联异常，请重新确认！！！"),
    PARAMS_ERROR(400,"参数异常"),
    SYSTEM_ERROR(400,"系统异常"),
    LACK_OF_OF_COMPETENCE(400,"您对该操作无权限"),
    DELETE_FILE_ERROR(400,"删除文件失败！"),
    ;

    private Integer code;
    private final String msg;

    ResultMassage(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ResultMassage(String msg) {
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public String msg() {
        return msg;
    }

}
