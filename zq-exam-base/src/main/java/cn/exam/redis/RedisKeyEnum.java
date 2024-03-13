package cn.exam.redis;

import lombok.Getter;

@Getter
public enum RedisKeyEnum {
    USER("LOGIN_USER_INFO","用户信息"),
    REASON("REASON","原因信息");
    private String code;
    private String desc;
    RedisKeyEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
