package com.wind.blog.model.emun;

/**
 * @author qianchun
 */
public enum BlogFrom {

    ALIYUN("aliyun", 1), CSDN("csdn", 2);

    private String type;

    private Integer value;

    BlogFrom(String type, Integer value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }
}
