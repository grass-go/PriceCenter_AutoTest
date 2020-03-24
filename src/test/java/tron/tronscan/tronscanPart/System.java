package tron.tronscan.tronscanPart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.common.utils.WebBrowser;

public class System {
  private static String URL = "https://tronscan.org/#/tools/system";
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
  public void testSystemInfo() throws Exception{
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[1]/tbody/tr[1]/td")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[2]/tbody/tr[1]/td")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[3]/tbody/tr[1]/td")).getText().isEmpty());
  }

  @Test(enabled = true)
  public void testFullnodeInfo() throws Exception{
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[2]/tbody/tr[2]/th/span")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[2]/tbody/tr[2]/td/a")).getText().isEmpty());
  }

  @Test(enabled = true)
  public void testSolidityInfo() throws Exception{
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[3]/tbody/tr[2]/th/span")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/table[3]/tbody/tr[2]/td/a")).getText().isEmpty());
  }

  @AfterMethod(enabled = true)
  public void end() throws Exception {
    WebBrowser.tearDownBrowser();
  }

}
