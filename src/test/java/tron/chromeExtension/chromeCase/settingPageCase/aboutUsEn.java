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

public class aboutUsEn extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P0"},
      description = "Change language to English",
      alwaysRun = true,
      enabled = true)
  public void test001changeLanguageToEnglishTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(4));
    waitingTime();
    click(settingPage.English_btn);
    waitingTime();
    String languageType = getText(settingPage.language_btn);
    log("languageType:" + languageType);
    Assert.assertEquals("Language", languageType);
  }

  @Test(
      dependsOnMethods = {"test001changeLanguageToEnglishTest"},
      groups = {"P1"},
      description = "Privacy policy",
      alwaysRun = true,
      enabled = true)
  public void test002privacyPolicyTest() throws Exception {
    SettingPage settingPage = new SettingPage(DRIVER);
    String url = Helper.getAboutUsLink(settingPage, 0);
    log("privacyPolicy_url:" + url);
    log("privacyReportUrlEn:" + privacyReportUrlJp);
    Assert.assertEquals(privacyReportUrlEn, url);
  }

  @Test(
      dependsOnMethods = {"test001changeLanguageToEnglishTest"},
      groups = {"P1"},
      description = "Audit report",
      alwaysRun = true,
      enabled = true)
  public void test003auditReportTest() throws Exception {

    SettingPage settingPage = new SettingPage(DRIVER);
    String url = Helper.getAboutUsLink(settingPage, 1);
    log("auditReport_url:" + url);
    log("auditReport:" + auditReport);
    Assert.assertEquals(auditReport, url);
  }

  @Test(
      dependsOnMethods = {"test001changeLanguageToEnglishTest"},
      groups = {"P1"},
      description = "Website",
      alwaysRun = true,
      enabled = true)
  public void test004websiteTest() throws Exception {

    SettingPage settingPage = new SettingPage(DRIVER);
    String url = Helper.getAboutUsLink(settingPage, 2);
    log("website_url:" + url);
    log("website:" + website);
    Assert.assertEquals(website, url);
  }

  @Test(
      dependsOnMethods = {"test001changeLanguageToEnglishTest"},
      groups = {"P1"},
      description = "Support center",
      alwaysRun = true,
      enabled = true)
  public void test005supportCenterTest() throws Exception {

    SettingPage settingPage = new SettingPage(DRIVER);
    String url = Helper.getAboutUsLink(settingPage, 3);
    log("SupportCenter_url:" + url);
    log("supportCenter:" + supportCenter);
    Assert.assertEquals(supportCenter, url);
  }

  @Test(
      dependsOnMethods = {"test001changeLanguageToEnglishTest"},
      groups = {"P1"},
      description = "Contact us.",
      alwaysRun = true,
      enabled = true)
  public void test006contactusTest() throws Exception {

    SettingPage settingPage = new SettingPage(DRIVER);
    String url = Helper.getAboutUsLink(settingPage, 4);
    log("Contactus_url:" + url);
    log("contactUs:" + contactUs);
    Assert.assertEquals(contactUs, url);
  }

  @Test(
      dependsOnMethods = {"test001changeLanguageToEnglishTest"},
      groups = {"P0"},
      description = "Change language to Chinese",
      alwaysRun = true,
      enabled = true)
  public void test007changeLanguageToChineseTest() throws Exception {

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

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
