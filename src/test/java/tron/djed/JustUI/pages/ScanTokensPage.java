package tron.djed.JustUI.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import tron.chromeExtension.pages.AbstractPage;

public class ScanTokensPage extends AbstractPage {

  public ScanTokensPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[1]/div[1]/div/div/div[2]/div/div[1]/canvas")
  public WebElement jstPrice_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/canvas")
  public WebElement usdjSupply_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[3]/div[1]/div/div/div[2]/div/div[1]/canvas")
  public WebElement ptrxTrxRatio_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[1]/div[2]/div/div/div[5]/div[1]/div[2]/a")
  public WebElement jstContractAddress_link;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[2]/div[2]/div/div/div[1]/div[2]/div")
  public WebElement totalSupply_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[2]/div[2]/div/div/div[3]/div[1]/div[2]/a")
  public WebElement usdjContractAddress_link;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[3]/div[2]/div/div/div[4]/div[1]/div[2]/a")
  public WebElement ptrxContractAddress_link;

  public ScanTokensPage enterScanTokensPage() throws Exception {
    try {
      TimeUnit.SECONDS.sleep(20);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    } catch (Exception e) {
      TimeUnit.SECONDS.sleep(20);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    return new ScanTokensPage(driver);
  }
}
