package tron.chromeExtension.chromeCase.settingPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;
import tron.common.utils.MyIRetryAnalyzer;

import java.util.concurrent.TimeUnit;

public class addAddressBook extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Add account to address book",
      alwaysRun = true,
      enabled = true)
  public void test001addAccountToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.addressBook_btn);
    waitingTime();
    click(settingPage.addAddressBook_btn);
    waitingTime();
    sendKeys(settingPage.name_input, "自动化测试账户1");
    sendKeys(settingPage.address_input, loginAddress);
    sendKeys(settingPage.remarks_input, "自动化测试账户1的地址");
    click(settingPage.save_btn);
    waitingTime(5);
    // 验证回到列表页面
    Assert.assertEquals("地址本", getText(settingPage.addressBook_content));
  }

  @Test(
      groups = {"P0"},
      description = "Add the same account to address book",
      alwaysRun = true,
      enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void test002addAccountToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.addressBook_btn);
    waitingTime();
    click(settingPage.addAddressBook_btn);
    waitingTime();
    sendKeys(settingPage.address_input, loginAddress);
    String tips = getText(settingPage.duplicateAddressTips);
    System.out.println("tips:" + tips);
    Assert.assertEquals(duplicateAddressTips, tips);
    click(settingPage.close_btn);
    waitingTime(5);
    // 验证回到列表页面
    Assert.assertEquals("地址本", getText(settingPage.addressBook_content));
  }

  @Test(
      groups = {"P0"},
      description = "Search account to address book",
      alwaysRun = true,
      enabled = true)
  public void test003searchAccountToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.addressBook_btn);
    waitingTime();
    sendKeys(settingPage.search_input, "自动化测试账户1");
    waitingTime();
    Assert.assertEquals("自动化测试账户1", getText(settingPage.searchResultName_content));
    Assert.assertEquals(
        "TG46VehcrBnw5mDPRvcSWFd1sRV85vKDDJ", getText(settingPage.searchResultAddress_content));
    Assert.assertEquals("备注：自动化测试账户1的地址", getText(settingPage.searchResultRemarks_content));
  }

  @Test(
      groups = {"P0"},
      description = "Search account to address book",
      alwaysRun = true,
      enabled = true)
  public void test004deleteAccountFromAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.addressBook_btn);
    waitingTime();
    sendKeys(settingPage.search_input, "自动化测试账户1");
    waitingTime();
    click(settingPage.searchResultName_content);
    waitingTime();
    click(settingPage.delete_btn);
    waitingTime();
    click(settingPage.confirm_btn);
    waitingTime();
    sendKeys(settingPage.search_input, "自动化测试账户1");
    waitingTime(5);
    Assert.assertEquals("未找到该地址", getText(settingPage.tips_content));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
