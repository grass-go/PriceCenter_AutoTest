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
  // 地址本按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[1]/div/div/span")
  public WebElement addressBook_btn;

  // 添加地址本按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]")
  public WebElement addAddressBook_btn;

  // 地址本中已存在
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[4]/span")
  public WebElement duplicateAddressTips;
  // 名称输入框
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[2]/input")
  public WebElement name_input;
  // 地址输入框
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[3]/div/input")
  public WebElement address_input;
  // 备注输入框
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[4]/input")
  public WebElement remarks_input;
  // 保存按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/button[1]")
  public WebElement save_btn;
  // 关闭按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/button[2]/span")
  public WebElement close_btn;
  // 地址本三个字位置
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/span")
  public WebElement addressBook_content;

  // 搜索输入框
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/input")
  public WebElement search_input;
  // 搜索结果的名称
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[1]/span/span")
  public WebElement searchResultName_content;

  // 搜索结果的地址
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[2]/span")
  public WebElement searchResultAddress_content;
  // 搜索结果的备注
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[3]")
  public WebElement searchResultRemarks_content;
  // 删除地址按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[1]/div/img")
  public WebElement delete_btn;

  // 确认删除按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[4]/button[2]")
  public WebElement confirm_btn;

  // 搜索无结果提示：未找到该地址
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


  // 自动锁定按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[6]/div/div[1]/span")
  public WebElement automaticLocking_btn;
  // 一分钟后自动锁定
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[6]/div/div[2]/div[1]/span")
  public  WebElement oneMinute_btn;

  // 永不自动锁定
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[6]/div/div[2]/div[6]/span")
  public WebElement neverAutomaticLocking_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[7]/div/div[1]/span")
  public WebElement ledgerConnection_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[8]/div/div/span")
  public WebElement aboutUs_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[9]/div/div/span")
  public WebElement logout_btn;
}
