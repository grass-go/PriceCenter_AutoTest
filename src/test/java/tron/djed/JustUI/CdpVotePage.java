package tron.djed.JustUI;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

public class CdpVotePage extends Base {

  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getString("JustIP");
  private  String URL = "https://"+tronScanNode+"/#/vote";
  Navigation navigation;

  @BeforeClass
  public void before() throws Exception{
    setUpChromeDriver();
    loginAccount();
    navigation = DRIVER.navigate();
    String WindowsTronLink = DRIVER.getWindowHandle();
    ((JavascriptExecutor)DRIVER).executeScript("window.open('" + URL + "')");
    Thread.sleep(3000);
    Set<String> allWindow = DRIVER.getWindowHandles();
    for(String i:allWindow){
      if (i != WindowsTronLink){ DRIVER.switchTo().window(i);}
    }
  }

  @BeforeMethod
  public void beforeTest() throws Exception{
    navigation.refresh();
    Thread.sleep(6000);
  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001VoteDis() throws Exception{
    // Welcome dashboard
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div[1]/div/div/div/div")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div[1]/div/div/div/div/div/div[3]/button")).isEnabled());

  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test002VoteDis() throws Exception{
    // click headLine [ Executive ] btn
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"voteIntro1\"]/button[1]")).isEnabled());
    DRIVER.findElement(By.xpath("//*[@id=\"voteIntro1\"]/button[1]")).click();
    Thread.sleep(1000);

    // Executive vote body
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div/div[1]/div/div")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div/div[1]/div/div/div[2]/div/div/div")).isEnabled());

    // click [ read_more ]
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div/div[1]/div/div/div[2]/div/div/div[1]")).click();
    Thread.sleep(3000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div/div[1]/div/div/div[2]/div/div/div[2]/div")).isDisplayed());

  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test003PollingDis() throws Exception{
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"voteIntro1\"]/button[2]")).isEnabled());
    DRIVER.findElement(By.xpath("//*[@id=\"voteIntro1\"]/button[2]")).click();
    Thread.sleep(3000);

    // PollingList__WalletBalance、Poll_body、read_more
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div/div[1]/div")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div/div[2]/div/div[1]/div/div")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div/div[2]/div/div[1]/div/div/div[2]/div")).isEnabled());

    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div/div[2]/div/div[1]/div/div/div[2]/div")).click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div/div[2]/div/div[1]/div/div/div[2]/div/div[2]")).isDisplayed());

  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class)
  public void test004LockJST() throws Exception{

    // [ lock JST now ]
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/section/section/main/div/div[1]/div/div/div/div/div/div[3]/button")).click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div/div/div[3]/div[3]/button")).isEnabled());
    Assert.assertTrue(DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div/div/div[4]/div[3]/button")).isEnabled());

    // click blank Element to close lockJSTPage
    DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[1]")).click();
    // new Actions(DRIVER).moveToElement(DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[1]"))).click().perform();

    // click headLine [ Lock JST ]
    DRIVER.findElement(By.xpath("//*[@id=\"voteIntro2\"]/button")).click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div/div/div[3]/div[3]/button")).isEnabled());
    Assert.assertTrue(DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div/div/div[4]/div[3]/button")).isEnabled());

    DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[1]")).click();
    // new Actions(DRIVER).moveToElement(DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[1]"))).click().perform();
  }


  @AfterClass(enabled = true)
  public void after() throws Exception {
    logoutAccount();

  }
}
