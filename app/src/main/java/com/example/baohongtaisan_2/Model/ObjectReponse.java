package com.example.baohongtaisan_2.Model;

public class ObjectReponse {
    private int code;
    private String message;

    public ObjectReponse() {
    }

    public ObjectReponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
