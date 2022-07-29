package tron.tronlink.v2.model;

public class RiskRsp {

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