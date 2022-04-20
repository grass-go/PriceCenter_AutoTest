package tron.chromeExtension.chromeCase.settingPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;

public class aboutUs extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P1"},
      description = "Privacy policy",
      alwaysRun = false,
      enabled = false)
  public void test001privacyPolicyCaseTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.aboutUs_btn);
    waitingTime();
    click(settingPage.privacyPolicy_btn);

    // switchWindows();
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
