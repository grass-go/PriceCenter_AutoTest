package tron.chromeExtension.chromeCase.settingPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;
import tron.chromeExtension.utils.Helper;

public class aboutUsCn extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Change language to Chinese",
      alwaysRun = true,
      enabled = true)
  public void test001changeLanguageToChineseTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(4));
    waitingTime(5);
    click(settingPage.Chinese_btn);
    waitingTime(5);
    String languageType = getText(settingPage.language_btn);
    log("languageType:" + languageType);
    Assert.assertEquals("语言", languageType);
  }

  @Test(
      groups = {"P1"},
      description = "Privacy policy",
      alwaysRun = true,
      enabled = true)
  public void test002privacyPolicyTest() throws Exception {
    SettingPage settingPage = new SettingPage(DRIVER);
    String url = Helper.getAboutUsLink(settingPage, 0);
    log("url:" + url);
    Assert.assertEquals(privacyReportUrlCn, url);
  }

  @Test(
      groups = {"P1"},
      description = "Audit report",
      alwaysRun = true,
      enabled = true)
  public void test003auditReportTest() throws Exception {

    SettingPage settingPage = new SettingPage(DRIVER);
    String url = Helper.getAboutUsLink(settingPage, 1);
    log("url:" + url);
    Assert.assertEquals(auditReport, url);
  }

  @Test(
      groups = {"P1"},
      description = "Website",
      alwaysRun = true,
      enabled = true)
  public void test004websiteTest() throws Exception {

    SettingPage settingPage = new SettingPage(DRIVER);
    String url = Helper.getAboutUsLink(settingPage, 2);
    log("url:" + url);
    Assert.assertEquals(websiteCn, url);
  }

  @Test(
      groups = {"P1"},
      description = "Support center",
      alwaysRun = true,
      enabled = true)
  public void test005SupportCenterTest() throws Exception {

    SettingPage settingPage = new SettingPage(DRIVER);
    String url = Helper.getAboutUsLink(settingPage, 3);
    log("url:" + url);
    Assert.assertEquals(supportCenterCn, url);
  }

  @Test(
      groups = {"P1"},
      description = "Contact us.",
      alwaysRun = true,
      enabled = true)
  public void test006ContactusTest() throws Exception {

    SettingPage settingPage = new SettingPage(DRIVER);
    String url = Helper.getAboutUsLink(settingPage, 4);
    log("url:" + url);
    Assert.assertEquals(contactUsCn, url);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
