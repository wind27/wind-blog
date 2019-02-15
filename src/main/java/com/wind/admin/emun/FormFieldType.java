package com.wind.admin.emun;

/**
 *
 * 表单属性类型
 * FormFieldType
 *
 * @author qianchun
 * @date 2019/1/31
 **/
public enum FormFieldType {

    /**
     * input text
     */
    INPUT_TEXT(1, "input_text"),

    /**
     * input checkbox
     */
    INPUT_CHECKOUT(1, "input_checkbox"),

    /**
     * file
     */
    INPUT_FILE(1, "input_file"),

    /**
     * input hidden
     */
    INPUT_HIDDEN(1, "input_hidden"),

    /**
     * input image
     */
    INPUT_IMAGE(1, "input_image"),

    /**
     * input password
     */
    INPUT_PASSPORT(1, "input_password"),

    /**
     * input radio
     */
    INPUT_RADIO(1, "input_radio"),

    /**
     * input reset
     */
    INPUT_RESET(1, "input_reset"),

    /**
     * input submit
     */
    INPUT_SUBMIT(1, "input_submit"),

    /**
     * input button
     */
    INPUT_BUTTON(1, "input_button"),


    /**
     * 文本框
     */
    TEXTAREA(1, "textarea"),

    /**
     * 选择框
     */
    SELECT(1, "select");
    ;


    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private int value;

    FormFieldType(int value, String name) {
        this.name = name;
        this.value = value;
    }
}
