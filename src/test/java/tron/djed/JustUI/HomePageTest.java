package tron.djed.JustUI;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver.Navigation;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

public class HomePageTest extends Base {

  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getString("JustIP");
  private  String URL = "https://"+tronScanNode+"/#/home";
  Navigation navigation;

  @BeforeClass
  public void before() throws Exception{
    setUpChromeDriver();
    loginAccount();
    navigation = DRIVER.navigate();
    String WindowsTronLink = DRIVER.getWindowHandle();
    ((JavascriptExecutor)DRIVER).executeScript("window.open('" + URL + "')");
    Thread.sleep(1000);
    Set<String> allWindow = DRIVER.getWindowHandles();
    for(String i:allWindow){
      if (i != WindowsTronLink){ DRIVER.switchTo().window(i);}
    }
    Thread.sleep(5000);
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div/button[1]"))
        .click();
    Thread.sleep(3000);
    DRIVER.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/div/div[2]/button"))
        .click();
    Thread.sleep(5000);
  }

  @BeforeMethod
  public void beforeTest() throws Exception{
    navigation.refresh();
    Thread.sleep(9000);
  }

  //@Test
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001HomeDis() throws Exception{
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div[3]/div[1]/div[2]/div[1]/header/h1")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div[3]/div[1]/div[2]/div[2]/div/div[1]/div[1]")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/aside/div/div[1]/table/tbody/tr[1]/td[2]/span")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/aside/div/div[1]/table/tbody/tr[2]/td[2]")).isDisplayed());

    // Latest Price
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/aside/div/div[2]/div/div[1]/div[1]/span/span[1]/span")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/aside/div/div[2]/div/div[3]/div[1]/span/span[1]/span")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/aside/div/div[2]/div/div[2]/div[1]/span/span[1]/span")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/aside/div/div[2]/div/div[4]/div[1]/span/span[1]/span")).isDisplayed());

    // Global CDP info
    Assert.assertEquals(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/aside/div/div[4]/div/h3[1]")).getText(),"CDP 全局质押率");
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/aside/div/div[4]/div/div[1]/span/span[1]")).isDisplayed());

    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/aside/div/div[4]/div/div[2]/span/span[1]/span")).isDisplayed());
    Assert.assertEquals(DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/aside/div/div[4]/div/h3[2]")).getText(),"USDJ 全网总量");
  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test002CdpDepositBtn() throws Exception{

    Thread.sleep(10000);

    // click [deposit] btn and deposit form display , Current statuation is displayed
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div[3]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/button")).click();
    Thread.sleep(2000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[2]")).isDisplayed());

    // deposit btn is enEnabled
    Assert.assertFalse(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[2]")).isEnabled());

    // set value 100 TRX and [deposit] btn is Enable
    DRIVER.findElement(By.xpath("//*[@id=\"inputValue\"]")).sendKeys("100");
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[2]")).isEnabled());
    Thread.sleep(1000);

    // [cancle]
    DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[1]")).click();
  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test003CdpWithdrawBtn() throws Exception{
    Thread.sleep(10000);
    // click [withdraw] btn and withdraw form display , Current statuation is displayed
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div[3]/div[1]/div[2]/div[1]/div[3]/div[1]/div[2]/div[3]/button")).click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[2]")).isDisplayed());

    // [withdraw] btn is enEnabled
    Assert.assertFalse(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[2]")).isEnabled());

    // set value 1 TRX  and withdraw btn is Enable
    DRIVER.findElement(By.xpath("//*[@id=\"inputValue\"]")).sendKeys("1");
    Thread.sleep(2000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[2]")).isEnabled());
    Thread.sleep(1000);

    // [cancle]
    DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[1]")).click();

  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test004CdpPayBackBtn() throws Exception{
    Thread.sleep(10000);
    // click [PayBack] btn and withdraw form display , Current statuation is displayed
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div[3]/div[1]/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/button")).click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[2]")).isDisplayed());

    // [PayBack] btn is enEnabled
    Assert.assertFalse(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[2]")).isEnabled());

    // set value 1 TRX  and withdraw btn is Enable
    DRIVER.findElement(By.xpath("//*[@id=\"inputValue\"]")).sendKeys("0.1");
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[2]")).isEnabled());
    Thread.sleep(1000);

    // [cancle]
    DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[1]")).click();

  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test005CdpGenerateBtn() throws Exception{
    // click [Generate] btn and withdraw form display , Current statuation is displayed
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div[3]/div[1]/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/button")).click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[2]")).isDisplayed());

    // [Generate] btn is enEnabled
    Assert.assertFalse(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[2]")).isEnabled());

    // set value 0.1 USDJ  and [Generate] btn is Enable
    DRIVER.findElement(By.xpath("//*[@id=\"inputValue\"]")).sendKeys("0.1");
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[2]")).isEnabled());
    Thread.sleep(1000);

    // [cancle]
    DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/button[1]")).click();

  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test006NewCdpBtn() throws Exception{
    // click [+] btn to new CDP
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div[1]/div[2]/div/div/div/div[1]/div[2]")).click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"newRow2\"]/div[1]")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"newRow2\"]/div[2]")).isDisplayed());

    // [COLLATERALIZE & generate USDJ] btn is disEnabled
    Assert.assertFalse(DRIVER.findElement(By.xpath("//*[@id=\"generateBtn\"]/button")).isEnabled());
    DRIVER.findElement(By.xpath("//*[@id=\"inputETH\"]")).sendKeys("105");
    DRIVER.findElement(By.xpath("//*[@id=\"inputDAI\"]")).sendKeys("1");
    Thread.sleep(1000);

    // [COLLATERALIZE & generate USDJ] btn is Enabled and  yellow warning is display
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"generateBtn\"]/button")).isEnabled());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"newCup\"]/div[7]/div")).isDisplayed());

    // [cancle]
    DRIVER.findElement(By.xpath("//*[@id=\"newCup\"]/div[9]/div/span[2]/button")).click();

  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test007MoveCdpBtn() throws Exception{
    // click [MOVE CDP]
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div[3]/div[1]/div[2]/div[1]/div[1]/a[1]")).click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div")).isDisplayed());
    Assert.assertFalse(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[2]/label/span[1]")).isSelected());
    Assert.assertFalse(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[4]/button[2]")).isEnabled());

    // [MOVE] btn is disEnabled , set Receiving address and select confirm choices
    DRIVER.findElement(By.xpath("//*[@id=\"inputValue\"]")).sendKeys("TAYzcfLovWdV83g25Apfd7BA67J44D5z5M");
    Thread.sleep(3000);
    Assert.assertFalse(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[5]/button[2]")).isEnabled());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[3]/label/span[1]/input")).isSelected());

    DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[2]/label")).click();
    Thread.sleep(3000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[5]/button[2]")).isEnabled());


    // [cancle]
    DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[5]/button[1]")).click();

  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test008CloseCdpBtn() throws Exception{
    // click [CLOSE CDP]
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div[3]/div[1]/div[2]/div[1]/div[1]/a[2]")).click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div")).isDisplayed());
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"govFeeMkr\"]")).isSelected());

    // select USDJ to pay
    DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[1]/div[6]/div/div/label")).click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[1]/div[6]/div/div/div")).isDisplayed());


    // [cancle]
    DRIVER.findElement(By.xpath("//*[@id=\"dialog\"]/div/form/div[2]/button[1]")).click();

  }


  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class)
  public void test009Exit() throws Exception {
    // exit
    DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[1]/div[2]/a")).click();
    Thread.sleep(1000);
    DRIVER.findElement(By.xpath("/html/body/div[2]/div/div/ul/li[2]/div")).click();
    Thread.sleep(1000);
    Assert.assertEquals("未连接钱包",
        DRIVER.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[2]/main/div/div/div[1]"))
            .getText());
    Assert.assertEquals("登录",
        DRIVER.findElement(
            By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div[1]/div[2]/div[1]/div/div/div"))
            .getText());
  }

  @AfterClass(enabled = true)
  public void after() throws Exception {
    logoutAccount();

  }
}
