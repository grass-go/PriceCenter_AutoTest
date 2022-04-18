package tron.chromeExtension.chromeCase.settingPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;
import tron.common.utils.MyIRetryAnalyzer;

import java.util.concurrent.TimeUnit;

public class automaticLocking extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Automatic locking after 1 minute",
      alwaysRun = true,
      enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001automaticLockingTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    LoginPage loginPage = new LoginPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.automaticLocking_btn);
    waitingTime();
    click(settingPage.oneMinute_btn);
    waitingTime(65);
    sendKeys(loginPage.password_input, password);
    waitingTime();
    click(loginPage.login_btn);
    // Login verification succeeded.
    waitingTime(5);
    Assert.assertTrue(onTheHomepageOrNot(loginAddress));
    waitingTime();
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.automaticLocking_btn);
    waitingTime();
    click(settingPage.neverAutomaticLocking_btn);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
