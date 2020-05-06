package tron.chromeExtension;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;
import java.io.File;

public class login {
    private String uniqueId = "ibnejdfjmmkpcnlpebklmnkoeoihofec";
    private String extensionDownload = "https://chrome-extension-downloader.com/ibnejdfjmmkpcnlpebklmnkoeoihofec/";
    private String url = "chrome-extension://" + uniqueId + "/packages/popup/build/index.html";
    ChromeOptions options = new ChromeOptions ();
    ChromeDriver driver;

    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            options.addArguments("--user-data-dir=/Users/wangzihe/Library/Application Support/Google/Chrome/");
            //options.addArguments("("--user-data-dir="+r"C:/Users/Administrator/AppData/Local/Google/Chrome/User Data/")");
            options.addArguments ("load-extension=/Users/wangzihe/Downloads/chrome-extension");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.addArguments("--enable-extensions");
            options.addArguments("--verbose");
            //options.addArguments("--whitelisted-ips='chrome-extension://ibnejdfjmmkpcnlpebklmnkoeoihofec/packages/popup/build/index.html'");
            driver = new ChromeDriver(options);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //@Test(enabled = true)
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
    public void testSRText() throws Exception{
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElementByXPath("//*[@id=\"root\"]/div/div[2]/div/input").sendKeys("2012qubeijing");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElementByXPath("//*[@id=\"root\"]/div/div[2]/button").click();
        Thread.sleep(9000);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Float trxBalance = Float.valueOf(driver.findElementByXPath("//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[2]/div/div[1]/div[2]/span[1]").getText());
        System.out.println("trx balance:" + trxBalance);
        Assert.assertTrue(trxBalance > 0);
    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        driver.close();
    }
}