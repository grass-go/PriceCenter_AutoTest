package tron.tools.DAO;

import tron.tools.db.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FProjectTokenInfoDao {


    private long id;
    private String projectId;
    private String tokenId;
    private String apy;
    private String baseApy;
    private String mintApy;
    private String contractAddress;
    private String incomeTokenId;
    private String collateralFactor;
    private String oneToExchangeRate;
    private String totalWithdraw;
    private String totalSupply;
    private java.sql.Timestamp createdTime;
    private java.sql.Timestamp updatedTime;
    private String depositedUsd;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }


    public String getApy() {
        return apy;
    }

    public void setApy(String apy) {
        this.apy = apy;
    }


    public String getBaseApy() {
        return baseApy;
    }

    public void setBaseApy(String baseApy) {
        this.baseApy = baseApy;
    }


    public String getMintApy() {
        return mintApy;
    }

    public void setMintApy(String mintApy) {
        this.mintApy = mintApy;
    }


    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }


    public String getIncomeTokenId() {
        return incomeTokenId;
    }

    public void setIncomeTokenId(String incomeTokenId) {
        this.incomeTokenId = incomeTokenId;
    }


    public String getCollateralFactor() {
        return collateralFactor;
    }

    public void setCollateralFactor(String collateralFactor) {
        this.collateralFactor = collateralFactor;
    }


    public String getOneToExchangeRate() {
        return oneToExchangeRate;
    }

    public void setOneToExchangeRate(String oneToExchangeRate) {
        this.oneToExchangeRate = oneToExchangeRate;
    }


    public String getTotalWithdraw() {
        return totalWithdraw;
    }

    public void setTotalWithdraw(String totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
    }


    public String getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }


    public java.sql.Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(java.sql.Timestamp createdTime) {
        this.createdTime = createdTime;
    }


    public java.sql.Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(java.sql.Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }


    public String getDepositedUsd() {
        return depositedUsd;
    }

    public void setDepositedUsd(String depositedUsd) {
        this.depositedUsd = depositedUsd;
    }

    public List<FProjectTokenInfoDao> query() throws Exception {
        Connection con = DBUtil.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select contract_address,deposited_usd from f_project_token_info");
        List<FProjectTokenInfoDao> fp = new ArrayList<FProjectTokenInfoDao>();
        FProjectTokenInfoDao f = null;
        while (rs.next()) {//如果对象中有数据，就会循环打印出来
            f = new FProjectTokenInfoDao();
            f.setContractAddress(rs.getString("contract_address"));
            f.setDepositedUsd(rs.getString("deposited_usd"));
            fp.add(f);
        }
        return fp;
    }

    public List<FProjectTokenInfoDao> queryTRX() throws Exception {
        Connection con = DBUtil.getConnection();
        Statement stmt = con.createStatement();
        String sql = "select contract_address,deposited_usd from f_project_token_info where token_id = 'T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb' and project_id = '2f38665c-7c74-4e63-bbdf-c69d6a623892'";
        ResultSet rs = stmt.executeQuery(sql);
        List<FProjectTokenInfoDao> fp = new ArrayList<FProjectTokenInfoDao>();
        FProjectTokenInfoDao f = null;
        while (rs.next()) {//如果对象中有数据，就会循环打印出来
            f = new FProjectTokenInfoDao();
            f.setDepositedUsd(rs.getString("deposited_usd"));
            fp.add(f);
        }
        return fp;
    }

}
