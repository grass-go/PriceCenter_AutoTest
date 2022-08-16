package tron.chromeExtension.chromeCase.settingPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

public class addAddressBook extends Base {
  String addressName = "Thisnameisverylongdoyouknow";
  String addressInput = "TAJu6ArV39Fvn12bx4LqdG9GXxU2MYQW6k";
  String remarksInput = "第一次创建";
  String remarksInputSecond = "第一次修改";

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainMain);
  }

  @Test(
      groups = {"P0"},
      description = "Add null name to address book failed.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001addNullNameToAddressBookTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addAddressBook_btn);
    waitingTime();
    sendKeys(settingPage.name_input, "");
    sendKeys(settingPage.address_input, "TAJu6ArV39Fvn12bx4LqdG9GXxU2MYQW6k");
    sendKeys(settingPage.remarks_input, "自动化测试账户1的地址");
    click(settingPage.save_btn);
    waitingTime();
    Assert.assertTrue(Helper.isElementExist("primary", "保存"));
  }

  @Test(
      groups = {"P0"},
      description = "Add long name to address book failed.",
      alwaysRun = true,
      enabled = true)
  public void test002addLongNameToAddressBookTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    // settingPage.addressBook_btn
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addAddressBook_btn);
    waitingTime();
    sendKeys(settingPage.name_input, addressName);
    sendKeys(settingPage.address_input, addressInput);
    sendKeys(settingPage.remarks_input, remarksInput);
    click(settingPage.save_btn);
    waitingTime(5);
    String addressNameFromPage = getText(settingPage.addressName_text);
    log("addressNameFromPage：" + addressNameFromPage);
    Assert.assertEquals(addressName.substring(0, 20), addressNameFromPage);
    String address = getText(settingPage.address_text);
    log("address：" + address);
    Assert.assertEquals(addressInput, address);
    String remarks = getText(settingPage.remarks_text);
    log("remarks：" + remarks);
    Assert.assertEquals("备注：" + remarksInput, remarks);
  }

  @Test(
      groups = {"P0"},
      description = "Modify address name  to address book",
      alwaysRun = true,
      enabled = true)
  public void test003modifyAddressNameToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    // addressBook_btn
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addressRecord);
    waitingTime();
    Helper.clickAndClearAndInput(settingPage.modifyName_input, addressBookName);
    click(settingPage.save_btn);
    waitingTime(5);
    String addressNameFromPage = getText(settingPage.addressName_text);
    log("addressNameFromPage：" + addressNameFromPage);
    Assert.assertEquals(addressBookName, addressNameFromPage);
    String address = getText(settingPage.address_text);
    log("address：" + address);
    Assert.assertEquals(addressInput, address);
    String remarks = getText(settingPage.remarks_text);
    log("remarks：" + remarks);
    Assert.assertEquals("备注：" + remarksInput, remarks);
  }

  @Test(
      groups = {"P0"},
      description = "Modify address name  to address book",
      alwaysRun = true,
      enabled = true)
  public void test004modifyAddressToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    // addressBook_btn
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addressRecord);
    waitingTime();
    Helper.clickAndClearAndInput(settingPage.modifyAddress_input, loginAddress);
    click(settingPage.save_btn);
    waitingTime(5);
    String addressNameFromPage = getText(settingPage.addressName_text);
    log("addressNameFromPage：" + addressNameFromPage);
    Assert.assertEquals(addressBookName, addressNameFromPage);
    String address = getText(settingPage.address_text);
    log("address：" + address);
    Assert.assertEquals(loginAddress, address);
    String remarks = getText(settingPage.remarks_text);
    log("remarks：" + remarks);
    Assert.assertEquals("备注：" + remarksInput, remarks);
  }

  @Test(
      groups = {"P0"},
      description = "Modify address name  to address book",
      alwaysRun = true,
      enabled = true)
  public void test005modifyRemarksToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addressRecord);
    waitingTime();
    Helper.clickAndClearAndInput(settingPage.modifyRemarks_text, remarksInputSecond);
    click(settingPage.save_btn);
    waitingTime(5);
    String addressNameFromPage = getText(settingPage.addressName_text);
    log("addressNameFromPage：" + addressNameFromPage);
    Assert.assertEquals(addressBookName, addressNameFromPage);
    String address = getText(settingPage.address_text);
    log("address：" + address);
    Assert.assertEquals(loginAddress, address);
    String remarks = getText(settingPage.remarks_text);
    log("remarks：" + remarks);
    Assert.assertEquals("备注：" + remarksInputSecond, remarks);
  }

  @Test(
      groups = {"P0"},
      description = "Add the same name to address book.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test006addTheSameToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addAddressBook_btn);
    waitingTime();
    sendKeys(settingPage.name_input, addressBookName);
    String tips = getText(settingPage.duplicateNameTips);
    log("tips:" + tips);
    Assert.assertEquals("名称已存在", tips);
    click(settingPage.close_btn);
    waitingTime(5);
    Assert.assertEquals("地址本", getText(settingPage.addressBook_content));
  }

  @Test(
      groups = {"P0"},
      description = "Search account to address book",
      alwaysRun = true,
      enabled = true)
  public void test007searchAccountToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    // settingPage.addressBook_btn
    click(settingPage.settingList.get(0));
    waitingTime();
    sendKeys(settingPage.search_input, addressBookName);
    waitingTime();
    Assert.assertEquals(addressBookName, getText(settingPage.searchResultName_content));
    Assert.assertEquals(loginAddress, getText(settingPage.searchResultAddress_content));
    Assert.assertEquals("备注：" + remarksInput, getText(settingPage.searchResultRemarks_content));
  }

  @Test(
      groups = {"P0"},
      description = "Search account to address book",
      alwaysRun = true,
      enabled = true)
  public void test008deleteAccountFromAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    // settingPage.addressBook_btn
    click(settingPage.settingList.get(0));
    waitingTime();
    sendKeys(settingPage.search_input, addressBookName);
    waitingTime();
    click(settingPage.searchResultName_content);
    waitingTime();
    click(settingPage.delete_btn);
    waitingTime();
    click(settingPage.confirm_btn);
    waitingTime();
    sendKeys(settingPage.search_input, addressBookName);
    waitingTime(5);
    Assert.assertEquals("未找到该地址", getText(settingPage.tips_content));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
