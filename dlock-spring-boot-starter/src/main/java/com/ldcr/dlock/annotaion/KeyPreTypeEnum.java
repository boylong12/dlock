package com.ldcr.dlock.annotaion;

/**
 * 锁名称前缀类型枚举类
 *
 * @author zhanghonglong
 * @date 2020/5/26 16:27
 */
public enum KeyPreTypeEnum {
    PACKAGE(1, "全类名为前缀"),
    CUSTOM(2, "自定义前缀");
    private int type;
    private String comment;

    KeyPreTypeEnum(int type, String comment) {
        this.type = type;
        this.comment = comment;
    }

    public int getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }
}
