package tron.chromeExtension.chromeCase.sendPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SendPage;
import tron.chromeExtension.pages.SettingPage;
import tron.chromeExtension.utils.Helper;

public class sendTRC10 extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
    Helper.switchAccount(testAccountTwoIndex, testAddress);
  }

  @Test(
      groups = {"P0"},
      description = "Send trc10 1004910 .",
      alwaysRun = true,
      enabled = true)
  public void test001sendTrc10Test() throws Exception {
    String transactionStatus = Helper.transfer(false,loginAddress, "1004910", "1", false);
    Assert.assertEquals("交易已广播", transactionStatus);
    Assert.assertTrue(onTheHomepageOrNot(testAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
