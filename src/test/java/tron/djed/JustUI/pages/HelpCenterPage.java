package tron.djed.JustUI.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import tron.chromeExtension.pages.AbstractPage;

public class HelpCenterPage extends AbstractPage {

  public HelpCenterPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@id=\"query\"]")
  public WebElement search_input;

  @FindBy(xpath = "//*[@id=\"main-content\"]/h1")
  public WebElement searchResult_text;

  @FindBy(xpath = "/html/body/footer/div/a")
  public WebElement home_link;

  @FindBy(xpath = "/html/body/main/div/section[1]/section[1]/ul/li[1]/a")
  public WebElement FAQ_link;

  @FindBy(css = "li[class='article-list-item article-promoted']")
  public List<WebElement> articlePromotedList1_text;

  @FindBy(css = "li[class='article-list-item']")
  public List<WebElement> articleList_text;

  @FindBy(xpath = "//*[@id=\"main-content\"]/section/a")
  public WebElement seeAllArticles_link;

  @FindBy(css = "li[class='article-list-item  article-promoted']")
  public List<WebElement> articlePromotedList2_text;

  @FindBy(css = "li[class='article-list-item ']")
  public List<WebElement> articleList2_text;

  @FindBy(xpath = "/html/body/main/div/section[1]/section[1]/ul/li[2]/a")
  public WebElement newUserGuide_link;

  @FindBy(xpath = "/html/body/main/div/section[1]/section[1]/ul/li[3]/a")
  public WebElement announcements_link;

  public HelpCenterPage enterHelpCenterPage() throws Exception {
    try {
      TimeUnit.SECONDS.sleep(10);
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    } catch (Exception e) {
      TimeUnit.SECONDS.sleep(10);
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    return new HelpCenterPage(driver);
  }
}
