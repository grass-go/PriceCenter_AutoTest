package tron.chromeExtension.chromeCase.sendPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SendPage;
import tron.chromeExtension.utils.Helper;

public class sendTRC20 extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
    Helper.switchAccount(testAccountMultiIndex, multiAddress);
  }

  @Test(
      groups = {"P0"},
      description = "Send trc20 JST .",
      alwaysRun = true,
      enabled = true)
  public void test001sendTrc20Test() throws Exception {
    String transactionStatus = Helper.transfer(loginAddress, "jst", "1", false);
    Assert.assertEquals("交易已广播", transactionStatus);
    Assert.assertTrue(onTheHomepageOrNot(multiAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
