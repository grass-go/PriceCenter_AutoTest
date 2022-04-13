package tron.chromeExtension.chromeCase.receiveTokenCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.ReceiveTokenPage;

import java.util.concurrent.TimeUnit;

public class receiveBalance extends Base {

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
  public void test001receiveBalanceTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    ReceiveTokenPage receiveTokenPage = new ReceiveTokenPage(DRIVER);
    waitingTime(5);
    click(mainPage.receive_btn);
    waitingTime(5);
    String receivePageAddressStr = getText(receiveTokenPage.accountAddress);
    System.out.println("receivePageAddressStr:" + receivePageAddressStr);
    Assert.assertEquals(loginAddress, receivePageAddressStr);
    String accountName = getText(receiveTokenPage.accountName);
    System.out.println("accountName:" + accountName);
    Assert.assertEquals("自动化测试账户1", accountName.split("\\+")[0]);
    // todo:二维码是图片，与导出的二维码的区别
    getText(receiveTokenPage.qRCode);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
