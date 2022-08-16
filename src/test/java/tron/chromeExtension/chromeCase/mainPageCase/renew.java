package tron.chromeExtension.chromeCase.mainPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LockPage;
import tron.chromeExtension.pages.MainPage;

import java.util.concurrent.TimeUnit;

public class renew extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
  }

  @Test(
      groups = {"P0"},
      description = "Renew page ",
      alwaysRun = true,
      enabled = false)
  public void test001renewPageTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.renew_btn);
    waitingTime(5);
    // todo:如何验证刷新成功？？？
    Assert.assertTrue(onTheHomepageOrNot(loginAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
