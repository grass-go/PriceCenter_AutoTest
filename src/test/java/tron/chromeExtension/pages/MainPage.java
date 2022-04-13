package tron.chromeExtension.pages;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.testng.Assert;

import static tron.chromeExtension.base.Base.*;

/** mine page */
public class MainPage extends AbstractPage {

  public MainPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }
  // 切换链的按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[1]")
  public WebElement selectedChain_btn;
  // 尼罗河网络
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[1]/div/div[4]")
  public WebElement nile;
  // 当前账户总资产
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[3]")
  public WebElement accountTotalBalance;
  // 当前账户地址
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[2]/span[1]")
  public WebElement address_content;
  // 当前账户名称
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[1]/span")
  public WebElement accountName_content;

  // 当前账户trx金额
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[2]/div/div[1]/div[3]/span[1]")
  public WebElement trxBalance;

  // 当前账户20币金额
  @FindBy(
      xpath =
          " //*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[2]/div/div[2]/div[3]/span[1]")
  public WebElement trc20Balance;
  // 当前账户10币金额
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[2]/div/div[2]/div[3]/span[1]")
  public WebElement trc10Balance;
  // 当前账户721币金额
  @FindBy(
      xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[2]/div/div[2]/div[3]")
  public WebElement trc721Balance;
  // 切换收藏品tab按钮
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[1]/div[1]/div[2]/span")
  public WebElement collectibles_btn;

  // 切换资产
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[1]/div[1]/div[1]/span")
  public WebElement assets_btn;
  // 切换账户按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[1]/span")
  public WebElement switchAccount_btn;
  // 所有账户总资产金额
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div[1]/div/span[2]")
  public WebElement totalBalanceOfAllAccounts;
  // 添加钱包
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[1]/a[1]")
  public WebElement addWallet_btn;

  // Dapp按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[1]/a[2]")
  public WebElement dApp_btn;

  // 热门列表页第一个元素
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]")
  public WebElement dAppHot_content;
  // 使用过按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/span")
  public WebElement used_btn;
  // 返回按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div")
  public WebElement back_btn;

  // 锁屏按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[2]/div[1]")
  public WebElement lock_btn;

  // 主页接收按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[1]/div[2]/span")
  public WebElement receive_btn;

  // 主页复制按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[2]/span[2]")
  public WebElement copy_btn;

  // 主页更多按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[2]/div[2]")
  public WebElement more_btn;

  // 修改账户名按钮
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div[1]/span[2]")
  public WebElement modifyAccountName_btn;

  // 修改账户名输入框
  @FindBy(xpath = "//*[@id=\"input\"]")
  public WebElement modifyAccountName_input;

  // 确认修改账户名称确认按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[3]/button[2]/span")
  public WebElement modifyAccountNameConfirm_btn;

  // 导出账户按钮
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div[2]/span[2]")
  public WebElement exportAccount_btn;
  // 链接管理按钮
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div[3]/span[2]")
  public WebElement manageConnections_btn;

  // 链接管理页面文案
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div/div[2]/span")
  public WebElement manageConnections_content;

  // 连接管理页面关闭按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div/div[1]")
  public WebElement manageConnectionsClose_btn;

  // 刷新按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[2]/div[3]")
  public WebElement renew_btn;
  // 设置按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[2]/div[5]")
  public WebElement set_btn;

  // 我已安全备份按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/button/span")
  public WebElement backedUp_btn;

  // 主页发送按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[4]/div[2]/span[2]")
  public WebElement send_btn;

  // 输入
  @FindBy(xpath = "//*[@id=\"input\"]")
  public WebElement password_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/button/span")
  public WebElement login_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[2]/div/div[3]/div[1]/span")
  public WebElement btt_btn;

  public SendPage enterSendPage() throws Exception {
    try {
      TimeUnit.SECONDS.sleep(2);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      send_btn.click();
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    } catch (Exception e) {
      TimeUnit.SECONDS.sleep(2);
      send_btn.click();
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    return new SendPage(driver);
  }

  public LockPage enterLockPage() throws Exception {
    try {
      TimeUnit.SECONDS.sleep(5);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      lock_btn.click();
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    } catch (Exception e) {

    }
    return new LockPage(driver);
  }
}
