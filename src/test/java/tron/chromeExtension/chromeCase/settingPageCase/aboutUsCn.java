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
      groups = {"P1"},
      description = "Privacy policy",
      alwaysRun = true,
      enabled = true)
  public void test001privacyPolicyTest() throws Exception {
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
  public void test002auditReportTest() throws Exception {

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
  public void test003websiteTest() throws Exception {

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
  public void test004SupportCenterTest() throws Exception {

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
  public void test005ContactusTest() throws Exception {

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
