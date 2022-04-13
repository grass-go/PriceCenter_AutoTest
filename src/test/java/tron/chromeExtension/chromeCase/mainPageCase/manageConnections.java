package tron.chromeExtension.chromeCase.mainPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.ExportAccountPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.ReceiveTokenPage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.util.concurrent.TimeUnit;

public class manageConnections extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount();
  }

  @Test(
      groups = {"P2"},
      description = "Manage connections",
      alwaysRun = true,
      enabled = true)
  public void test001manageConnectionsTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waiteTime(5);
    click(mainPage.more_btn);
    waiteTime();
    click(mainPage.manageConnections_btn);
    waiteTime();
    String content = getText(mainPage.manageConnections_content);
    System.out.println("content:" + content);
    Assert.assertEquals("已连接的网站", content);
    click(mainPage.manageConnectionsClose_btn);
    // 验证回到首页
    Assert.assertTrue(onTheHomepageOrNot(loginAddress));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
