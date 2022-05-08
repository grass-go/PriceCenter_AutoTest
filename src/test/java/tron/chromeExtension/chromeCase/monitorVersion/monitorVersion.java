package tron.chromeExtension.chromeCase.monitorVersion;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

public class monitorVersion extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
  }

  @Test(
      groups = {"P0"},
      description = "Monitor Version",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001monitorVersionTest() throws Exception {
    String nowExtensionVersion =
        Configuration.getByPath("testng.conf").getString("chromeExtension.nowExtensionVersion");

    String url =
        "https://chrome.google.com/webstore/detail/tronlink/" + Base.UNIQUEID + "?hl=zh-CN";
    log("url:" + url);
    DRIVER.get(url);
    waitingTime(10);
    String result =
        DRIVER
            .findElement(
                By.xpath(
                    "/html/body/div[3]/div[2]/div/div/div[3]/div[3]/div[1]/div/div[2]/div[2]/div[3]/span[4]"))
            .getText();

    waitingTime(5);
    log("插件当前版本：" + result);
    Assert.assertTrue(result.contains(nowExtensionVersion));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
