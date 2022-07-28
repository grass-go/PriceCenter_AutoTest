package tron.tronlink.v2.model;

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
    private double trxCount;
    private double usdCount;
    private double cnyCount;
    private double price;
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
    private String issueTime;
    private String issueAddress;
    private long totalSupply;
    private String totalSupplyStr;
    private int marketId;
    private int recommandSortId;
    private int tokenStatus;
    private boolean transferStatus;

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return this.type;
    }
    public void setTop(int top) {
        this.top = top;
    }
    public int getTop() {
        return this.top;
    }
    public void setIsOfficial(int isOfficial) {
        this.isOfficial = isOfficial;
    }
    public int getIsOfficial() {
        return this.isOfficial;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getShortName() {
        return this.shortName;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return this.id;
    }
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
    public String getContractAddress() {
        return this.contractAddress;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public int getBalance() {
        return this.balance;
    }
    public void setBalanceStr(String balanceStr) {
        this.balanceStr = balanceStr;
    }
    public String getBalanceStr() {
        return this.balanceStr;
    }
    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }
    public int getTotalBalance() {
        return this.totalBalance;
    }
    public void setTrxCount(double trxCount) {
        this.trxCount = trxCount;
    }
    public double getTrxCount() {
        return this.trxCount;
    }
    public void setUsdCount(double usdCount) {
        this.usdCount = usdCount;
    }
    public double getUsdCount() {
        return this.usdCount;
    }
    public void setCnyCount(double cnyCount) {
        this.cnyCount = cnyCount;
    }
    public double getCnyCount() {
        return this.cnyCount;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return this.price;
    }
    public void setUsdPrice(String usdPrice) {
        this.usdPrice = usdPrice;
    }
    public String getUsdPrice() {
        return this.usdPrice;
    }
    public void setCnyPrice(String cnyPrice) {
        this.cnyPrice = cnyPrice;
    }
    public String getCnyPrice() {
        return this.cnyPrice;
    }
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    public String getLogoUrl() {
        return this.logoUrl;
    }
    public void setPrecision(int precision) {
        this.precision = precision;
    }
    public int getPrecision() {
        return this.precision;
    }
    public void setInMainChain(boolean inMainChain) {
        this.inMainChain = inMainChain;
    }
    public boolean getInMainChain() {
        return this.inMainChain;
    }
    public void setInSideChain(boolean inSideChain) {
        this.inSideChain = inSideChain;
    }
    public boolean getInSideChain() {
        return this.inSideChain;
    }
    public void setMaincontractAddress(String maincontractAddress) {
        this.maincontractAddress = maincontractAddress;
    }
    public String getMaincontractAddress() {
        return this.maincontractAddress;
    }
    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }
    public String getHomePage() {
        return this.homePage;
    }
    public void setIsInAssets(boolean isInAssets) {
        this.isInAssets = isInAssets;
    }
    public boolean getIsInAssets() {
        return this.isInAssets;
    }
    public void setIsShield(boolean isShield) {
        this.isShield = isShield;
    }
    public boolean getIsShield() {
        return this.isShield;
    }
    public void setTokenDesc(String tokenDesc) {
        this.tokenDesc = tokenDesc;
    }
    public String getTokenDesc() {
        return this.tokenDesc;
    }
    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }
    public String getIssueTime() {
        return this.issueTime;
    }
    public void setIssueAddress(String issueAddress) {
        this.issueAddress = issueAddress;
    }
    public String getIssueAddress() {
        return this.issueAddress;
    }
    public void setTotalSupply(long totalSupply) {
        this.totalSupply = totalSupply;
    }
    public long getTotalSupply() {
        return this.totalSupply;
    }
    public void setTotalSupplyStr(String totalSupplyStr) {
        this.totalSupplyStr = totalSupplyStr;
    }
    public String getTotalSupplyStr() {
        return this.totalSupplyStr;
    }
    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }
    public int getMarketId() {
        return this.marketId;
    }
    public void setRecommandSortId(int recommandSortId) {
        this.recommandSortId = recommandSortId;
    }
    public int getRecommandSortId() {
        return this.recommandSortId;
    }
    public void setTokenStatus(int tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
    public int getTokenStatus() {
        return this.tokenStatus;
    }
    public void setTransferStatus(boolean transferStatus) {
        this.transferStatus = transferStatus;
    }
    public boolean getTransferStatus() {
        return this.transferStatus;
    }
}
