package tron.chromeExtension.chromeCase.sendPageCase;

import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SendPage;
import tron.common.utils.MyIRetryAnalyzer;

public class sendBtt extends  Base{
    public double beforeBalance;
    public double afterBalance;
    public double sendAmount = 0.000001;
    @BeforeMethod
    public void before() throws Exception{
        setUpChromeDriver();
        loginAccount();
    }

    //@Test
  //  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
    @Test(enabled = true)
    public void test001SendBttTest() throws Exception{
        SendPage sendPage = new MainPage(DRIVER).enterSendPage();
        System.out.println(sendPage.balanceInSendPage_text.getText());
        sendPage.receiverAddress_input.sendKeys(accountAddress002);
        TimeUnit.SECONDS.sleep(2);
        sendPage.select_coin_type_btn.click();
        TimeUnit.SECONDS.sleep(2);
        sendPage.btt_in_selection_btn.click();
        TimeUnit.SECONDS.sleep(2);
        sendPage.amount_input.sendKeys(String.valueOf(sendAmount));
        TimeUnit.SECONDS.sleep(2);
        beforeBalance = getBalanceFromSelectionBtn(sendPage.select_coin_type_btn.getText());
        System.out.println("beforebalance:" + beforeBalance);
        sendPage.send_btn.click();
        TimeUnit.SECONDS.sleep(30);
    }

   // @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
    @Test(enabled = true)
    public void test002SendBttCheckTest() throws Exception{
        SendPage sendPage = new MainPage(DRIVER).enterSendPage();
        TimeUnit.SECONDS.sleep(2);
        sendPage.select_coin_type_btn.click();
        TimeUnit.SECONDS.sleep(2);
        sendPage.btt_in_selection_btn.click();
        DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        afterBalance = getBalanceFromSelectionBtn(sendPage.select_coin_type_btn.getText());
        System.out.println("btt beforeBalance:"+ beforeBalance);
        System.out.println("btt afterbalance:"+ afterBalance);
        Assert.assertNotEquals(beforeBalance,afterBalance);
        //Assert.assertTrue(beforeBalance - sendAmount == afterBalance);

    }

    @AfterMethod(enabled = true)
    public void after() throws Exception {
        logoutAccount();

    }
}
