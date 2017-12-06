package com.donggua.enums;

/**
 * 封装全局统一响应结构
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-07-27 上午 09:51
 */
public class JSONDataObject<T> {

    /**
     * 响应状态码
     */
    private Integer retCode;

    /**
     * 响应消息
     */
    private String retMsg;

    /**
     * 响应数据
     */
    private T data;

    public JSONDataObject() {
    }

    public JSONDataObject(Integer retCode, String retMsg, T data) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = data;
    }

    public JSONDataObject(EnumKeyValue status, T data) {
        this.retCode = status.code();
        this.retMsg = status.message();
        this.data = data;
    }

    public JSONDataObject(Status status) {
        this.retCode = status.code();
        this.retMsg = status.message();
        this.data = null;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JSONDataObject{" +
                "retCode=" + retCode +
                ", retMsg='" + retMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
