package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;
import tron.common.utils.Configuration;

public class NodesTest {private String tronScanNode = Configuration.getByPath("testng.conf")
    .getString("tronscanIP");
private  String URL = "https://"+tronScanNode+"/#/blockchain/nodes";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;
    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
    public void testSRText() throws Exception{
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div[1]/div/div/h3")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div[2]/div/div/h3")).getText().isEmpty());
    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}