package tron.common.utils;

import com.alibaba.fastjson.JSONObject;

import junit.framework.TestResult;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import tron.tronscan.monitor.blockchain_transactions_page;

public class RetryTronscanMonitor implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 3;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getString("tronscanIP");
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;


    public boolean retry(ITestResult result) {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            String URL = "https://"+tronScanNode+"/#/blockchain/transactions";
            driver = webBrowser.startChrome(URL);
            Thread.sleep(5000);
            Object a = result.getInstance();
            File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File("/Users/tron/.jenkins/workspace/screenshot/"+result.getName()+sdf.format(date)+".png"));
        } catch (Exception e) {
        }
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }

}
