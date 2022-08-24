package tron.tronlink.v2.model;

// UnfollowAssertList接口返回值，直接用
public class UnfollowAssertListRsp {
    private int code;
    private String message;
    private Data data;

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
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return this.data;
    }
}
