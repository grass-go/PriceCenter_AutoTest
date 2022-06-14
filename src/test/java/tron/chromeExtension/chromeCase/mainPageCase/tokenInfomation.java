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
      description = "Verify that the TRX icon has a recommendation flag.",
      alwaysRun = true,
      enabled = true)
  public void test001TRXIconHasARecommendationFlag() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    waitingTime(3);
    WebElement trxDiv = mainPage.token_list.get(0);
    waitingTime(3);
    Assert.assertTrue(
        trxDiv.findElement(new By.ByClassName("logo"))
            .findElements(By.cssSelector("img[alt]"))
            .get(1).getAttribute("src").contains("/static/media/icon-verify.d25d69e8.svg"));
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
