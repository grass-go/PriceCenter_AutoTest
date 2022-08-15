package tron.tronlink.v2.model.trc1155;

import tron.tronlink.v2.model.Data;

// AllCollectionRsp 直接用
public class AllCollectionRsp {
    private int code;
    private String message;
    private tron.tronlink.v2.model.Data data;

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return this.code;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
    public void setData(tron.tronlink.v2.model.Data data) {
        this.data = data;
    }
    public Data getData() {
        return this.data;
    }
}
