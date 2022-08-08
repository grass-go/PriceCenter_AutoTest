package tron.tronlink.v2.model;

/**
 * 很多接口的返回值都是这些字段，直接复用
 */
public class CommonRsp {

    private int code;
    private String message;
    private boolean data;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setData(boolean data) {
        this.data = data;
    }
    public boolean getData() {
        return data;
    }

}

