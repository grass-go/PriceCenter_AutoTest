package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/** mine page */
public class SendPage extends AbstractPage {

  public SendPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div/textarea")
  public WebElement receiverAddress_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/button")
  public WebElement next_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div/div")
  public WebElement trx_neirong;

  @FindBy(xpath = "//*[@id=\"tokenSelect\"]/div/div/div[2]/div/div/div[2]/div[1]/span/input")
  public WebElement search_input;

  @FindBy(xpath = "//*[@id=\"tokenSelect\"]/div/div/div[2]/div/div/div[2]/div[3]/div")
  public WebElement search_result;

  @FindBy(xpath = "//*[@id=\"input\"]")
  public WebElement amount_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/button")
  public WebElement transfer_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div[3]/span")
  public WebElement memo_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/div[3]/div[2]/textarea")
  public WebElement memo_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[3]/div[4]/button[2]")
  public WebElement signature_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/h1/span")
  public WebElement transactionStatus;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/button[1]/span")
  public WebElement complete_btn;
}
