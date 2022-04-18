package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/** mine page */
public class SettingPage extends AbstractPage {

  public SettingPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }
  // Address book button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[1]/div/div/span")
  public WebElement addressBook_btn;

  // Add address book button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]")
  public WebElement addAddressBook_btn;

  // Address book already exists.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[4]/span")
  public WebElement duplicateAddressTips;
  // Name input box.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[2]/input")
  public WebElement name_input;
  // Address input box.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[3]/div/input")
  public WebElement address_input;
  // Note input box.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[4]/input")
  public WebElement remarks_input;
  // Save button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/button[1]")
  public WebElement save_btn;
  // Close button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/button[2]/span")
  public WebElement close_btn;
  // Address book three word position.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/span")
  public WebElement addressBook_content;

  // Search input box.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/input")
  public WebElement search_input;
  // The name of the search result.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[1]/span/span")
  public WebElement searchResultName_content;

  // Address of search results.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[2]/span")
  public WebElement searchResultAddress_content;
  // Comments for search results.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[3]")
  public WebElement searchResultRemarks_content;
  // Delete address button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[1]/div/img")
  public WebElement delete_btn;

  // Confirm delete button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[4]/button[2]")
  public WebElement confirm_btn;

  // No search results prompt: the address was not found.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/span")
  public WebElement tips_content;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div/span")
  public WebElement dAPPWhiteList_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[3]/div/div/span")
  public WebElement nodeManagement_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[4]/div/div[1]/span")
  public WebElement valuationCurrency_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[5]/div/div[1]/span")
  public WebElement language_btn;

  // Auto lock button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[6]/div/div[1]/span")
  public WebElement automaticLocking_btn;
  // Automatic locking after one minute.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[6]/div/div[2]/div[1]/span")
  public WebElement oneMinute_btn;

  // Never auto lock.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[6]/div/div[2]/div[6]/span")
  public WebElement neverAutomaticLocking_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[7]/div/div[1]/span")
  public WebElement ledgerConnection_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[8]/div/div/span")
  public WebElement aboutUs_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[9]/div/div/span")
  public WebElement logout_btn;
}