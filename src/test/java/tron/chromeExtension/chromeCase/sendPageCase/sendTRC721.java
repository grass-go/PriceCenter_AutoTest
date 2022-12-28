package tron.chromeExtension.chromeCase.sendPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SendPage;
import tron.chromeExtension.utils.Helper;

public class sendTRC721 extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
    Helper.switchAccount(testAccountOneIndex, loginAddress);
  }

  @Test(
      groups = {"P0"},
      description = "Send trc721 TYN .",
      alwaysRun = true,
      enabled = true)
  public void test001sendTrc721Test() throws Exception {

    String transactionStatus =
        Helper.transfer(false, testAddress, "TVzRKmCZ471QGsKqFJbXc2qeNtJNmcumbR", "1", true);
    Assert.assertEquals("交易已广播", transactionStatus);
    Assert.assertTrue(onTheHomepageOrNot(loginAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
