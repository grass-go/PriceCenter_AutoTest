package tron.chromeExtension.chromeCase.mainPageCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.AllAssetsPage;
import tron.chromeExtension.pages.MainPage;

public class tokenInfomation extends Base {

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainMain);
  }

  @Test(
      groups = {"P0"},
      description = " Verify that the TRX icon contains the letter V.",
      alwaysRun = true,
      enabled = true)
  public void test001TRXTestIconContainsLetterV() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(3);
    WebElement temp = mainPage.token_list.get(0);
    waitingTime(3);
    Assert.assertEquals(
        2,
        temp.findElement(new By.ByClassName("logo"))
            .findElements(By.cssSelector("img[alt]"))
            .size());
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
