package tron.tronlink.v2.model.trc1155.search;

import java.util.Date;


public class Token {

    private int type;
    private int top;
    private int isOfficial;
    private String name;
    private String shortName;
    private String id;
    private String contractAddress;
    private int balance;
    private String balanceStr;
    private int totalBalance;
    private int trxCount;
    private int usdCount;
    private int cnyCount;
    private int price;
    private String usdPrice;
    private String cnyPrice;
    private String logoUrl;
    private int precision;
    private boolean inMainChain;
    private boolean inSideChain;
    private String maincontractAddress;
    private String homePage;
    private boolean isInAssets;
    private boolean isShield;
    private String tokenDesc;
    private Date issueTime;
    private String issueAddress;
    private int totalSupply;
    private String totalSupplyStr;
    private int marketId;
    private int recommandSortId;
    private int tokenStatus;
    private boolean transferStatus;
    private int matchField;
    private int nrOfTokenHolders;
    private int transferCount;
    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setTop(int top) {
        this.top = top;
    }
    public int getTop() {
        return top;
    }

    public void setIsOfficial(int isOfficial) {
        this.isOfficial = isOfficial;
    }
    public int getIsOfficial() {
        return isOfficial;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getShortName() {
        return shortName;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
    public String getContractAddress() {
        return contractAddress;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    public int getBalance() {
        return balance;
    }

    public void setBalanceStr(String balanceStr) {
        this.balanceStr = balanceStr;
    }
    public String getBalanceStr() {
        return balanceStr;
    }

    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }
    public int getTotalBalance() {
        return totalBalance;
    }

    public void setTrxCount(int trxCount) {
        this.trxCount = trxCount;
    }
    public int getTrxCount() {
        return trxCount;
    }

    public void setUsdCount(int usdCount) {
        this.usdCount = usdCount;
    }
    public int getUsdCount() {
        return usdCount;
    }

    public void setCnyCount(int cnyCount) {
        this.cnyCount = cnyCount;
    }
    public int getCnyCount() {
        return cnyCount;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }

    public void setUsdPrice(String usdPrice) {
        this.usdPrice = usdPrice;
    }
    public String getUsdPrice() {
        return usdPrice;
    }

    public void setCnyPrice(String cnyPrice) {
        this.cnyPrice = cnyPrice;
    }
    public String getCnyPrice() {
        return cnyPrice;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    public String getLogoUrl() {
        return logoUrl;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
    public int getPrecision() {
        return precision;
    }

    public void setInMainChain(boolean inMainChain) {
        this.inMainChain = inMainChain;
    }
    public boolean getInMainChain() {
        return inMainChain;
    }

    public void setInSideChain(boolean inSideChain) {
        this.inSideChain = inSideChain;
    }
    public boolean getInSideChain() {
        return inSideChain;
    }

    public void setMaincontractAddress(String maincontractAddress) {
        this.maincontractAddress = maincontractAddress;
    }
    public String getMaincontractAddress() {
        return maincontractAddress;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }
    public String getHomePage() {
        return homePage;
    }

    public void setIsInAssets(boolean isInAssets) {
        this.isInAssets = isInAssets;
    }
    public boolean getIsInAssets() {
        return isInAssets;
    }

    public void setIsShield(boolean isShield) {
        this.isShield = isShield;
    }
    public boolean getIsShield() {
        return isShield;
    }

    public void setTokenDesc(String tokenDesc) {
        this.tokenDesc = tokenDesc;
    }
    public String getTokenDesc() {
        return tokenDesc;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }
    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueAddress(String issueAddress) {
        this.issueAddress = issueAddress;
    }
    public String getIssueAddress() {
        return issueAddress;
    }

    public void setTotalSupply(int totalSupply) {
        this.totalSupply = totalSupply;
    }
    public int getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupplyStr(String totalSupplyStr) {
        this.totalSupplyStr = totalSupplyStr;
    }
    public String getTotalSupplyStr() {
        return totalSupplyStr;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }
    public int getMarketId() {
        return marketId;
    }

    public void setRecommandSortId(int recommandSortId) {
        this.recommandSortId = recommandSortId;
    }
    public int getRecommandSortId() {
        return recommandSortId;
    }

    public void setTokenStatus(int tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
    public int getTokenStatus() {
        return tokenStatus;
    }

    public void setTransferStatus(boolean transferStatus) {
        this.transferStatus = transferStatus;
    }
    public boolean getTransferStatus() {
        return transferStatus;
    }

    public void setMatchField(int matchField) {
        this.matchField = matchField;
    }
    public int getMatchField() {
        return matchField;
    }

    public void setNrOfTokenHolders(int nrOfTokenHolders) {
        this.nrOfTokenHolders = nrOfTokenHolders;
    }
    public int getNrOfTokenHolders() {
        return nrOfTokenHolders;
    }

    public void setTransferCount(int transferCount) {
        this.transferCount = transferCount;
    }
    public int getTransferCount() {
        return transferCount;
    }

}