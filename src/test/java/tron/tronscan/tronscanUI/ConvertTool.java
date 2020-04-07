package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.common.utils.Configuration;
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
      java.lang.System.out.println(e);
    }
  }

  @Test(enabled = true)
  public void testBase64() throws Exception{
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[1]/tbody/tr[1]/td")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[2]/tbody/tr[1]/td")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[3]/tbody/tr[1]/td")).getText().isEmpty());
  }

  @Test(enabled = false)
  public void testBase58() throws Exception{
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[2]/tbody/tr[2]/th/span")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[2]/tbody/tr[2]/td/a")).getText().isEmpty());
  }


  @Test(enabled = false)
  public void testPubKeyAddress() throws Exception{
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[3]/tbody/tr[2]/th/span")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[3]/tbody/tr[2]/td/a")).getText().isEmpty());
  }

  @Test(enabled = false)
  public void testMainnet() throws Exception{
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[3]/tbody/tr[2]/th/span")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[3]/tbody/tr[2]/td/a")).getText().isEmpty());
  }

  @AfterMethod(enabled = false)
  public void end() throws Exception {
    WebBrowser.tearDownBrowser();
  }

}
