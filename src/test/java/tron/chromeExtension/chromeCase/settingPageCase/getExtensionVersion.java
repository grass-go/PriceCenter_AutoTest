package tron.chromeExtension.chromeCase.settingPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;

public class getExtensionVersion extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
  }

  @Test(
      groups = {"P0"},
      description = "Get extension Version",
      alwaysRun = true,
      enabled = true)
  public void test001getExtensionVersionTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    String version = getText(settingPage.version);
    Assert.assertEquals(nowVersion, version);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
