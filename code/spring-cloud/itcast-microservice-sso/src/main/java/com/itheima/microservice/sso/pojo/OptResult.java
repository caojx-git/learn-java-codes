package com.itheima.microservice.sso.pojo;

/**
 * Created by Administrator on 2017/12/10.
 */
public class OptResult {

    private int state;//操作结果 1:成功，0：失败
    private String message;//提示信息
    private Object data;//数据对象

    public OptResult(int state, String message, Object data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public OptResult() {
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
