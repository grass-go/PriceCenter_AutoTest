package tron.tronlink.v2.model;

import java.util.List;

public class Data {

    private int count;
    private List<Token> token;

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return this.count;
    }
    public void setToken(List<Token> token) {
        this.token = token;
    }
    public List<Token> getToken() {
        return this.token;
    }
}
