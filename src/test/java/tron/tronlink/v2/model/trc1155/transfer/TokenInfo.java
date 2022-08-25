package tron.tronlink.v2.model.trc1155.transfer;


public class TokenInfo {

    private String tokenId;
    private String tokenAbbr;
    private String tokenName;
    private int tokenDecimal;
    private int tokenCanShow;
    private String tokenType;
    private String tokenLogo;
    private String tokenLevel;
    private boolean vip;
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenAbbr(String tokenAbbr) {
        this.tokenAbbr = tokenAbbr;
    }
    public String getTokenAbbr() {
        return tokenAbbr;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }
    public String getTokenName() {
        return tokenName;
    }

    public void setTokenDecimal(int tokenDecimal) {
        this.tokenDecimal = tokenDecimal;
    }
    public int getTokenDecimal() {
        return tokenDecimal;
    }

    public void setTokenCanShow(int tokenCanShow) {
        this.tokenCanShow = tokenCanShow;
    }
    public int getTokenCanShow() {
        return tokenCanShow;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenLogo(String tokenLogo) {
        this.tokenLogo = tokenLogo;
    }
    public String getTokenLogo() {
        return tokenLogo;
    }

    public void setTokenLevel(String tokenLevel) {
        this.tokenLevel = tokenLevel;
    }
    public String getTokenLevel() {
        return tokenLevel;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
    public boolean getVip() {
        return vip;
    }

}