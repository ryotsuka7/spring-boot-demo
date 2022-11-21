package com.example.springbootdemo.messages;

import java.util.ResourceBundle;

public enum ErrorMessage {
    NOT_FOUND(100001, "該当するレコードがありません。({0})", "No record found for id.({0})"),
    XXX_YYY(100002, "デモメッセージ。({0}, {1})", "Demo message.({0}, {1})");

    // Enumでは@Valueでプロパティ値が取得できないため、ResourceBundleを使用
    private static final String RESOURCE_NAME = "application";
    private static final ResourceBundle rb = ResourceBundle.getBundle(RESOURCE_NAME);
    private final int code;
    private final String messageJa;
    private final String messageEn;

    ErrorMessage(int code, String messageJa, String messageEn) {
        this.code = code;
        this.messageJa = messageJa;
        this.messageEn = messageEn;
    }

    public String getMessage() {
        String message;
        String lang = rb.getString("spring-boot-demo.lang");
        switch (lang) {
            case "ja":
                message = this.messageJa;
                break;
            case "en":
            default:
                message = this.messageEn;
        }
        return message;
    }

    public int getCode() {
        return this.code;
    }
}
