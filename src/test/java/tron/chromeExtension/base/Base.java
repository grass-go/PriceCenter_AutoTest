package tron.chromeExtension.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.common.utils.Configuration;

public class Base {

    public ChromeDriver DRIVER;
    static String UNIQUEID = Configuration.getByPath("testng.conf")
      .getString("chromeExtension.uniqueId");
    static String PAGEPATH = Configuration.getByPath("testng.conf")
      .getString("chromeExtension.pagePath");
    static String userDataDir = Configuration.getByPath("testng.conf")
      .getString("chromeExtension.userDataDir");
    static String extensionDir = Configuration.getByPath("testng.conf")
      .getString("chromeExtension.extensionDir");
    static String password = Configuration.getByPath("testng.conf")
      .getString("chromeExtension.password");
    public String accountAddress001 = Configuration.getByPath("testng.conf")
      .getString("chromeExtension.accountAddress001");
    public String accountKey001 = Configuration.getByPath("testng.conf")
      .getString("chromeExtension.accountKey001");
    public String accountAddress002 = Configuration.getByPath("testng.conf")
      .getString("chromeExtension.accountAddress002");
    public String accountKey002 = Configuration.getByPath("testng.conf")
      .getString("chromeExtension.accountKey002");
    public String URL = "chrome-extension://" + UNIQUEID + PAGEPATH;
    ChromeOptions OPTION = new ChromeOptions ();
    Integer retryLoginTimes = 5;



    public void setUpChromeDriver()throws Exception {
      killChromePid();
      try {
        OPTION.addArguments("--user-data-dir=" + userDataDir);
        OPTION.addArguments ("load-extension=" + extensionDir);
        OPTION.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        OPTION.addArguments("--enable-extensions");
        OPTION.addArguments("--verbose");
        DRIVER = new ChromeDriver(OPTION);
      } catch (Exception e) {
        e.printStackTrace();

      }
    }

    public boolean loginAccount() throws Exception{
      while (retryLoginTimes > 0) {
        try {
          retryLoginTimes--;
          DRIVER.get(URL);
          try {
            if (new MainPage(DRIVER).send_btn.isEnabled()){
              return true;
            }
          } catch (Exception e) {
          }
          DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
          LoginPage loginPage = new LoginPage(DRIVER);
          loginPage.password_input.sendKeys(password);
          DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
          loginPage.login_btn.click();
          DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
          Thread.sleep(6000);
          Float trxBalance = Float.valueOf(loginPage.trxBalance.getText());
          if (trxBalance > 0) {
            return true;
          }
          DRIVER.quit();
        } catch (Exception e) {
          e.printStackTrace();
          DRIVER.quit();
          setUpChromeDriver();
        }
      }
      return false;

  }



    public void logoutAccount() throws Exception {
      DRIVER.close();
    }


    public double getBalanceFromSelectionBtn(String string) {
      System.out.println("string:" + string);
      int left = string.indexOf('(');
      int right = string.indexOf(')');
      return Double.valueOf(string.substring(left+1,right));
    }

    public  static void killChromePid() throws IOException {
      Process process = Runtime.getRuntime().exec("sh kill_chrome.sh");
    }

  public boolean elementIsExist(By locator) {
    try {
      DRIVER.findElement(locator);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
