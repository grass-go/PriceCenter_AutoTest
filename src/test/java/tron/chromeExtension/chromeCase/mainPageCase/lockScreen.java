package tron.chromeExtension.chromeCase.mainPageCase;

import lombok.var;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LockPage;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SendPage;

import java.util.concurrent.TimeUnit;

public class lockScreen extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Lock screen and unlock successfully ",
      alwaysRun = true,
      enabled = true)
  public void test001lockScreeTest() throws Exception {

    LockPage lockPage = new MainPage(DRIVER).enterLockPage();
    waitingTime(5);
    sendKeys(lockPage.password_input, password);
    click(lockPage.login_btn);
    waitingTime(5);
    // 验证解锁成功
    Assert.assertTrue(onTheHomepageOrNot(loginAddress));
  }

  @Test(
      groups = {"P0"},
      description = "Lock screen and unlock failed ",
      alwaysRun = true,
      enabled = true)
  public void test002lockScreeTest() throws Exception {

    LockPage lockPage = new MainPage(DRIVER).enterLockPage();
    waitingTime(5);
    sendKeys(lockPage.password_input, passwordWrong);
    click(lockPage.login_btn);
    waitingTime(5);
    // 验证解锁失败
    Assert.assertEquals(lockPageTips1, getText(lockPage.tips1));
    Assert.assertEquals(lockPageTips2, getText(lockPage.tips2));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
