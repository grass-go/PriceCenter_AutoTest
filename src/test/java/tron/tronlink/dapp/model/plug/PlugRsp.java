package tron.tronlink.dapp.model.plug;

import java.util.List;

public class PlugRsp {
    private int ret;
    private String msg;
    private List<Data> data;
    public void setRet(int ret) {
        this.ret = ret;
    }
    public int getRet() {
        return ret;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }
}