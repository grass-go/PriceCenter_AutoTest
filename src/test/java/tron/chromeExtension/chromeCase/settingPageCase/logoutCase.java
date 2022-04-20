package tron.chromeExtension.chromeCase.settingPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.ReceiveTokenPage;
import tron.chromeExtension.pages.SettingPage;

import java.util.concurrent.TimeUnit;

public class logoutCase extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Receive balance page",
      alwaysRun = true,
      enabled = true)
  public void test001logoutCaseTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    LoginPage loginPage = new LoginPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(8));
    waitingTime();
    sendKeys(loginPage.password_input, password);
    waitingTime();
    click(loginPage.login_btn);
    // Login verification succeeded.
    waitingTime(5);
    Assert.assertTrue(onTheHomepageOrNot(loginAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
