package tron.chromeExtension.chromeCase.mainPageCase;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.utils.Helper;

public class copyAccountAddress extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainNile);
    Helper.switchAccount(testAccountOneIndex, loginAddress);
  }

  @Test(
      groups = {"P0"},
      description = "Receive balance page",
      alwaysRun = true,
      enabled = true)
  public void test001copyAccountAddressTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(5);
    click(mainPage.copy_btn);
    waitingTime(5);
    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    String accountAddress = fetchClipboardContents(clip);
    log("accountAddress: " + accountAddress);
    Assert.assertNotNull(accountAddress);
    Assert.assertEquals(loginAddress, accountAddress);
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
