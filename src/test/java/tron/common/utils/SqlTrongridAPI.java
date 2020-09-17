package tron.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.xml.XmlSuite;
import tron.common.TronscanApiList;

public class SqlTrongridAPI implements IReporter{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://39.105.200.151:3306/AutoTestScan";
    static final String USER = "AutoTestScan";
    static final String PASS = "root";
    String time = "";
    int status = 0;
    String sucessClass = "";
    int sucessnum = 0;
    String failClass = "";
    int failnum = 0;
    int sum =0;


    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        List<ITestResult> list = new ArrayList<ITestResult>();
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult suiteResult : suiteResults.values()) {
                ITestContext testContext = suiteResult.getTestContext();
                IResultMap passedTests = testContext.getPassedTests();
                IResultMap failedTests = testContext.getFailedTests();
                IResultMap skippedTests = testContext.getSkippedTests();
                IResultMap failedConfig = testContext.getFailedConfigurations();
                list.addAll(this.listTestResult(passedTests));
                list.addAll(this.listTestResult(failedTests));
                list.addAll(this.listTestResult(skippedTests));
                list.addAll(this.listTestResult(failedConfig));
            }
        }
        this.sort(list);
        this.outputResult(list);
    }

    private ArrayList<ITestResult> listTestResult(IResultMap resultMap){
        Set<ITestResult> results = resultMap.getAllResults();
        return new ArrayList<ITestResult>(results);
    }

    private void sort(List<ITestResult> list){
        Collections.sort(list, new Comparator<ITestResult>() {
            @Override
            public int compare(ITestResult r1, ITestResult r2) {
                if(r1.getStartMillis()>r2.getStartMillis()){
                    return 1;
                }else{
                    return -1;
                }
            }
        });
    }

    private void outputResult(List<ITestResult> list){
        List success = new ArrayList();
        List fail = new ArrayList();
        StringBuffer sb = new StringBuffer();
        for (ITestResult result : list) {
            if (sb.length() != 0) {
                sb.append("\r\n");
            }
            if (result.getStatus() == 1) {
                success.add(result.getMethod().getMethodName());
            } else  if(result.getStatus() == 2){
                fail.add(result.getMethod().getMethodName());
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        sucessnum = success.size();
        failnum = fail.size();
        sum = failnum + sucessnum;
        time = formatter.format(date).toString();
        sucessClass = success.toString().replaceAll("(?:\\[|null|\\]| +)", "");
        if (fail.isEmpty()) {
            status = 1;
        } else {
            failClass = fail.toString().replaceAll("(?:\\[|null|\\]| +)", "");
            status = 2;
        }
        mysql();

    }


    public void mysql(){
        HttpResponse response = TronscanApiList.getBuildId("http://tronlink:tronlink@172.16.22.178:8080/job/Trongrid_Api/api/json");
        JSONObject responseContent = TronscanApiList.parseResponseContent(response).getJSONObject("lastBuild");
        int buildid = responseContent.getInteger("number");

        Connection conn = null;
        Statement stmt = null;
        String result = "";
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = "INSERT INTO `AutoTestScan`.`trongridAPI`(`time`, `status`, `sucessclass`, `sucessnum`,`failClass`,`failnum`,`sum`,`buildid`) VALUES ('"+time+"','"+status+"','"+sucessClass+"','"+sucessnum+"','"+failClass+"','"+failnum+"','"+sum+"','"+buildid+"')";
            stmt.executeUpdate(sql);
            System.out.println(result);
            stmt.close();
            conn.close();

        }catch (Exception e){
            System.out.println(e);
        }
    }


    private String getStatus(int status){
        String statusString = null;
        switch (status) {
            case 1:
                statusString = "SUCCESS";
                break;
            case 2:
                statusString = "FAILURE";
                break;
            case 3:
                statusString = "SKIP";
                break;
            default:
                break;
        }
        return statusString;
    }

    private String formatDate(long date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

}