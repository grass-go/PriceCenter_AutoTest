package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;

public class ConvertTool {
  private String tronScanNode = Configuration.getByPath("testng.conf")
    .getString("tronscanIP");
private  String URL = "https://"+tronScanNode+"/#/tools/tron-convert-tool";
  WebBrowser webBrowser = new WebBrowser();
  public static WebDriver driver;
  @BeforeMethod(enabled = true)
  public void start() throws Exception {
    try {
      driver = webBrowser.startChrome(URL);
    } catch (Exception e) {
    }
  }

  @Test(enabled = true,description = "编译器工具-Base64",retryAnalyzer = MyIRetryAnalyzer.class)
  public void testBase64() throws Exception{
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[4]/h4/span/span")).getText().isEmpty();
    driver.switchTo().frame("convertTool");
    driver.findElement(By.cssSelector("body > div > div > div > h3")).getText().isEmpty();
    driver.findElement(By.cssSelector(".tabbable > ul > li:nth-child(1) > a")).getText().isEmpty();
    driver.findElement(By.cssSelector(".tabbable > ul > li:nth-child(1) > a")).click();
    Thread.sleep(200);
    driver.findElement(By.cssSelector(".tabbable > div > div:nth-child(1) > form > p")).getText().isEmpty();
    driver.close();
  }

  @Test(enabled = true,description = "编译器工具-Base58",retryAnalyzer = MyIRetryAnalyzer.class)
  public void testBase58() throws Exception{
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[4]/h4/span/span")).getText().isEmpty();
    driver.switchTo().frame("convertTool");
    driver.findElement(By.cssSelector("body > div > div > div > h3")).getText().isEmpty();
    driver.findElement(By.cssSelector(".tabbable > ul > li:nth-child(2) > a")).getText().isEmpty();
    driver.findElement(By.cssSelector(".tabbable > ul > li:nth-child(2) > a")).click();
    Thread.sleep(200);
    driver.findElement(By.cssSelector(".tabbable > div > div:nth-child(2) > form > p")).getText().isEmpty();
    driver.close();
  }


  @Test(enabled = true,description = "编译器工具-PubKeyAddress",retryAnalyzer = MyIRetryAnalyzer.class)
  public void testPubKeyAddress() throws Exception{
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[4]/h4/span/span")).getText().isEmpty();
    driver.switchTo().frame("convertTool");
    driver.findElement(By.cssSelector("body > div > div > div > h3")).getText().isEmpty();
    driver.findElement(By.cssSelector(".tabbable > ul > li:nth-child(3) > a")).getText().isEmpty();
    driver.findElement(By.cssSelector(".tabbable > ul > li:nth-child(3) > a")).click();
    Thread.sleep(200);
    driver.findElement(By.cssSelector(".tabbable > div > div:nth-child(3) > form > p:nth-child(3)")).getText().isEmpty();
    driver.close();
  }

  @Test(enabled = true,description = "编译器工具-Mainnet",retryAnalyzer = MyIRetryAnalyzer.class)
  public void testMainnet() throws Exception{
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[4]/h4/span/span")).getText().isEmpty();
    driver.switchTo().frame("convertTool");
    driver.findElement(By.cssSelector("body > div > div > div > h3")).getText().isEmpty();
    driver.findElement(By.cssSelector(".tabbable > ul > li:nth-child(5) > a")).getText().isEmpty();
    driver.findElement(By.cssSelector(".tabbable > ul > li:nth-child(5) > a")).click();
    Thread.sleep(200);
    driver.findElement(By.cssSelector(".tabbable > div > div:nth-child(5) > form > p")).getText().isEmpty();
    driver.close();
  }

  @AfterMethod(enabled = true)
  public void end() throws Exception {
    WebBrowser.tearDownBrowser();
  }

}
