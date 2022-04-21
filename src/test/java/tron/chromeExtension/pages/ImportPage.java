package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/** mine page */
public class ImportPage extends AbstractPage {

  public ImportPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[4]/div[1]")
  public WebElement addWallet_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[4]/div[2]")
  public WebElement importWallet_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[3]/span")
  public WebElement agree_btn;

  // 内嵌窗口
  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[2]")
  public WebElement embeddedWindow;

  // todo:滚动条位置无法定位
  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[2]")
  public WebElement scrollBar;
}
