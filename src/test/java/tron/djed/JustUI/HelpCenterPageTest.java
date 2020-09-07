package tron.djed.JustUI;

import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.djed.JustUI.pages.HelpCenterPage;

public class HelpCenterPageTest extends Base {

  private String node = Configuration.getByPath("testng.conf")
      .getString("JustHelpCenterIP");
  private String URL = "https://" + node + "/hc/en-us";
  Navigation navigation;
  HelpCenterPage helpCenterPage;
  String justLink;

  @BeforeClass
  public void before() throws Exception {
    setUpChromeDriver();
    DRIVER.get(URL);
    navigation = DRIVER.navigate();
    helpCenterPage = new HelpCenterPage(DRIVER).enterHelpCenterPage();
    justLink = DRIVER.getWindowHandle();
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "search")
  public void test001Search() throws Exception {
    helpCenterPage.search_input.sendKeys("how");
    helpCenterPage.search_input.sendKeys(Keys.ENTER);
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.getCurrentUrl()
        .contains("https://justorg.zendesk.com/hc/en-us/search?utf8=%E2%9C%93&query=how"));
    System.out.println(
        "helpCenterPage.search_input.getText():" + helpCenterPage.searchResult_text.getText());
    Assert.assertTrue(helpCenterPage.searchResult_text.getText()
        .contains("results for \"how\" in All Categories"));
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "FAQ")
  public void test002FAQ() throws Exception {
    helpCenterPage.FAQ_link.click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.getCurrentUrl()
        .contains("https://justorg.zendesk.com/hc/en-us/categories/360003497791-FAQ"));
    List<WebElement> articlePromotedList1_text = helpCenterPage.articlePromotedList1_text;
    Assert.assertTrue(articlePromotedList1_text.size() > 0);
    List<WebElement> articleList_text = helpCenterPage.articleList_text;
    Assert.assertTrue(articleList_text.size() > 0);

    helpCenterPage.seeAllArticles_link.click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.getCurrentUrl()
        .contains("https://justorg.zendesk.com/hc/en-us/sections/360009209132-FAQ"));
    List<WebElement> articlePromotedList2_text = helpCenterPage.articlePromotedList2_text;
    Assert.assertTrue(articlePromotedList2_text.size() > 0);
    List<WebElement> articleList2_text = helpCenterPage.articleList2_text;
    Assert.assertTrue(articleList2_text.size() > 0);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "newUserGuide")
  public void test003NewUserGuide() throws Exception {
    helpCenterPage.newUserGuide_link.click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.getCurrentUrl()
        .contains("https://justorg.zendesk.com/hc/en-us/categories/360003497751-New-User-Guide"));
    List<WebElement> articlePromotedList1_text = helpCenterPage.articlePromotedList1_text;
    Assert.assertTrue(articlePromotedList1_text.size() > 0);
    List<WebElement> articleList_text = helpCenterPage.articleList_text;
    Assert.assertTrue(articleList_text.size() > 0);

    helpCenterPage.seeAllArticles_link.click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.getCurrentUrl()
        .contains("https://justorg.zendesk.com/hc/en-us/sections/360009319691-New-User-Guide"));
    List<WebElement> articlePromotedList2_text = helpCenterPage.articlePromotedList2_text;
    Assert.assertTrue(articlePromotedList2_text.size() > 0);
    List<WebElement> articleList2_text = helpCenterPage.articleList2_text;
    Assert.assertTrue(articleList2_text.size() > 0);
  }

  @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "announcements")
  public void test004Announcements() throws Exception {
    helpCenterPage.announcements_link.click();
    Thread.sleep(1000);
    Assert.assertTrue(DRIVER.getCurrentUrl()
        .contains("https://justorg.zendesk.com/hc/en-us/categories/360003475692-Announcements"));
    List<WebElement> articlePromotedList1_text = helpCenterPage.articlePromotedList1_text;
    Assert.assertTrue(articlePromotedList1_text.size() > 0);
  }

  @AfterMethod(enabled = true)
  public void afterMethod() {
    helpCenterPage.home_link.click();
  }

  @AfterClass(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }

}
