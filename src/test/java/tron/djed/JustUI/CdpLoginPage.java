package tron.djed.JustUI;


import java.util.Set;
import javax.print.DocFlavor.BYTE_ARRAY;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver.Navigation;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;


public class CdpLoginPage extends Base{
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getString("JustIP");
  private  String URL = "https://"+tronScanNode+"/#/login";
  Navigation navigation;


  @BeforeClass
  public void before() throws Exception{
    setUpChromeDriver();
    loginAccount();
    navigation = DRIVER.navigate();
    String WindowsTronLink = DRIVER.getWindowHandle();
    ((JavascriptExecutor)DRIVER).executeScript("window.open('" + URL + "')");
    Thread.sleep(2000);
    Set<String> allWindow = DRIVER.getWindowHandles();
    for(String i:allWindow){
      if (i != WindowsTronLink){ DRIVER.switchTo().window(i);}
    }
  }

  //@Test
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001HomeBtn() throws Exception{
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[1]/main/div/div/div/div/div/div[1]/div/div/div/a")).isDisplayed());
    DRIVER.findElement(By.xpath(("//*[@id=\"root\"]/div[1]/div[2]/div[1]/main/div/div/div"
        + "/div/div/div[1]/div/div/div/a/div"))).click();
    Thread.sleep(300);
    Assert.assertEquals(DRIVER.getCurrentUrl(),"https://just.tronscan.io/#/home");
    navigation.back();
    DRIVER.findElement(By.xpath("//*[@id=\"portalIntro1\"]/ul/li[1]/a")).click();
    Assert.assertEquals(DRIVER.getCurrentUrl(),"https://just.tronscan.io/#/home");
    navigation.back();
  }

  //@Test
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test002ScanBtn() throws Exception{
    Assert.assertEquals(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[1]/main"
        + "/div/div/div/div/div/div[2]/div/div/div/a")).getAttribute("href"),"#scan");
    DRIVER.findElement(By.xpath(("//*[@id=\"portalIntro1\"]/ul/li[2]/a"))).click();
    Assert.assertEquals(DRIVER.getCurrentUrl(),"https://just.tronscan.io/#/scan");
    navigation.back();

  }

  //@Test
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test003GovBtn() throws Exception{
    Assert.assertEquals(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[1]/main"
        + "/div/div/div/div/div/div[3]/div/div/div/a")).getAttribute("href"),"#vote");
    DRIVER.findElement(By.xpath(("//*[@id=\"portalIntro1\"]/ul/li[3]/a"))).click();
    Assert.assertEquals(DRIVER.getCurrentUrl(),"https://just.tronscan.io/#/vote");
    navigation.back();
  }

  @AfterClass(enabled = true)
  public void after() throws Exception {
        logoutAccount();

  }
}
