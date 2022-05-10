package tron.chromeExtension.chromeCase.sendPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SendPage;

public class sendTrx extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Send trx .",
      alwaysRun = true,
      enabled = true)
  public void test001sendTrxTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SendPage sendPage = new SendPage(DRIVER);
    waitingTime();
    click(mainPage.transfer_btn);
    waitingTime();
    sendKeys(sendPage.receiverAddress_input,loginAddress);
    waitingTime();
    click(sendPage.next_btn);
    waitingTime();
    click(sendPage.trx_neirong);
    sendKeys(sendPage.search_input,"TF17BgPaZYbz8oxbjhriubPDsA7ArKoLX3");
    waitingTime();
    click(sendPage.search_result);
    waitingTime();
    sendKeys(sendPage.amount_input,"1");
    waitingTime();
    click(sendPage.transfer_btn);
    waitingTime();
    click(sendPage.signature_btn);
    waitingTime(5);
    click(sendPage.complete_btn);
    waitingTime(5);
    Assert.assertTrue(onTheHomepageOrNot(loginAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
