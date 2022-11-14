package tron.chromeExtension.chromeCase.mainPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.utils.Helper;

public class accountAddress extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
    Helper.switchAccount(testAccountOneIndex, loginAddress);
  }

  @Test(
      groups = {"P2"},
      description = "Test home account  address format.",
      alwaysRun = true,
      enabled = true)
  public void test001HomeAccountAddressDisplayAddressFormatTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    String address = getText(mainPage.address_btn);
    log("address:" + address);
    Assert.assertFalse(address.contains("..."));
  }

  @Test(
      groups = {"P0"},
      description = "Test pledge myself to get bandwidth address format. ",
      alwaysRun = true,
      enabled = true)
  public void test002PledgeMyselfToGetBandwidthAddressFormatTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.pledge_btn);
    waitingTime(5);
    String minorHandle = DRIVER.getWindowHandle();
    switchWindows(minorHandle);
    waitingTime();
    String tips = Helper.pledgeTrxForMyself("width", true, minorHandle);
    Assert.assertTrue(tips.equals("Address Format True"));
  }

  @Test(
      groups = {"P0"},
      description = "Test send TRX 1004910 address format.",
      alwaysRun = true,
      enabled = true)
  public void test003SendTrxAddressFormatTest() throws Exception {
    Helper.switchAccount(testAccountTwoIndex, testAddress);
    String transactionStatus = Helper.transfer(true, loginAddress, "trx", "1", false);
    Assert.assertTrue(transactionStatus.equals("Address Format True"));
  }

  @Test(
      groups = {"P0"},
      description = "Test send trc10 1004910 address format. ",
      alwaysRun = true,
      enabled = true)
  public void test004SendTrc10AddressFormatTest() throws Exception {
    Helper.switchAccount(testAccountTwoIndex, testAddress);
    String transactionStatus = Helper.transfer(true, loginAddress, "1004910", "1", false);
    Assert.assertTrue(transactionStatus.equals("Address Format True"));
  }

  @Test(
      groups = {"P0"},
      description = "Test send trc20 JST address format .",
      alwaysRun = true,
      enabled = true)
  public void test005SendTrc20AddressFormatTest() throws Exception {
    Helper.switchAccount(testAccountTwoIndex, testAddress);
    String transactionStatus = Helper.transfer(true, loginAddress, "jst", "1", false);
    Assert.assertTrue(transactionStatus.equals("Address Format True"));
  }

  @Test(
      groups = {"P0"},
      description = "Test send trc721 TYN address format .",
      alwaysRun = true,
      enabled = true)
  public void test006SendTrc721AddressFormatTest() throws Exception {
    Helper.switchAccount(testAccountTwoIndex, testAddress);
    String transactionStatus =
        Helper.transfer(true, loginAddress, "TVzRKmCZ471QGsKqFJbXc2qeNtJNmcumbR", "1", true);
    Assert.assertTrue(transactionStatus.equals("Address Format True"));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
