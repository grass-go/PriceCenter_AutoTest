package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;

public class Committee {
  private String tronScanNode = Configuration.getByPath("testng.conf")
    .getString("tronscanIP");
private  String URL = "https://"+tronScanNode+"/#/sr/committee";
  WebBrowser webBrowser = new WebBrowser();
  public static WebDriver driver;
  @BeforeMethod(enabled = true)
  public void start() throws Exception {
    try {
      driver = webBrowser.startChrome(URL);
    } catch (Exception e) {

    }
  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "提议-tron网络参数")
  public void testCommittee() throws Exception{
    //定位提议标题
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[4]/h4/span/span")).getText().isEmpty());
    //tron网络参数定位
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div[2]/a[1]/span")).getText().isEmpty());
    //以下列表
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div/div/div/div/div/table/tbody/tr[1]/td[1]/span")).getText().isEmpty());
  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "提议-委员会提议")
  public void testSRCommittee() throws Exception{
    driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div[2]/a[2]/span")).click();
    //定位发起和我的提议
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div[1]/a[1]/span")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.cssSelector("div> div.proposal-header > a:nth-child(2) > span")).getText().isEmpty());
    //委员会列表
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div[2]/div/div/div/div/div/table/tbody/tr[1]/td[1]/div")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div[2]/div/div/div/div/div/table/tbody/tr[1]/td[2]/div/div/div/div")).getText().isEmpty());
  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void end() throws Exception {
    WebBrowser.tearDownBrowser();
  }
}
