package tron.tronlink.v2.model.trc1155.search;

import java.util.List;

public class Data {


    private int count;
    private List<Token> token;
    private String word;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setToken(List<Token> token) {
        this.token = token;
    }

    public List<Token> getToken() {
        return token;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

}