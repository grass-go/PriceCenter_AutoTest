package tron.tronlink.v2.model.trc1155;


import java.util.Date;

public class Data {

    private int type;
    private int top;
    private int isOfficial;
    private String name;
    private String shortName;
    private String contractAddress;
    private int count;
    private String logoUrl;
    private boolean inMainChain;
    private boolean inSideChain;
    private String maincontractAddress;
    private String homePage;
    private String tokenDesc;
    private Date issueTime;
    private String issueAddress;
    private int nrOfTokenHolders;
    private int transferCount;
    private int totalSupply;
    private String totalSupplyStr;
    private int marketId;
    private int tokenStatus;
    private boolean transferStatus;
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

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
    public String getContractAddress() {
        return contractAddress;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    public String getLogoUrl() {
        return logoUrl;
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

}