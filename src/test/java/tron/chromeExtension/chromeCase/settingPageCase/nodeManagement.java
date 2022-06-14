package tron.chromeExtension.chromeCase.settingPageCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.LoginPage;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class nodeManagement extends Base {

    @BeforeMethod
    public void before() throws Exception {
        setUpChromeDriver();
        loginAccount(chainNile);
    }

    @Test(
            groups = {"P1"},
            description = "Add node",
            alwaysRun = true,
            enabled = true, retryAnalyzer = MyIRetryAnalyzer.class)
    public void test001addNodeFailedTest() throws Exception {

        MainPage mainPage = new MainPage(DRIVER);
        SettingPage settingPage = new SettingPage(DRIVER);
        waitingTime(5);
        click(mainPage.set_btn);
        waitingTime();
        click(settingPage.settingList.get(2));
        waitingTime();
        click(settingPage.addNode_btn);
        String nodeNameStr = "测试添加";
        Helper.clickAndClearAndInput(settingPage.nodeName_input, nodeNameStr);
        log("Tips:" + getText(settingPage.nodeName_tips));
        Assert.assertEquals("需大于 4 个字符", getText(settingPage.nodeName_tips));
        String fullNodeInput = "1";
        Helper.clickAndClearAndInput(settingPage.wrongFullNode_input, fullNodeInput);
        log("Tips:" + getText(settingPage.wrongFullNode_input));
        Assert.assertEquals("格式错误，请以http://或https://开头", getText(settingPage.fullNode_tips));
        String eventServerInput = "1";
        Helper.clickAndClearAndInput(settingPage.wrongEventServer_input, eventServerInput);
        log("Tips:" + getText(settingPage.wrongEventServer_input));
        Assert.assertEquals("格式错误，请以http://或https://开头", getText(settingPage.eventServer_tips));
        click(settingPage.loseFocus);
        click(settingPage.back);
    }

    @Test(
            groups = {"P1"},
            description = "Add node success.",
            alwaysRun = true,
            enabled = true)
    public void test002addNodeSuccessTest() throws Exception {

        MainPage mainPage = new MainPage(DRIVER);
        SettingPage settingPage = new SettingPage(DRIVER);
        waitingTime(5);
        click(mainPage.set_btn);
        waitingTime();
        click(settingPage.settingList.get(2));
        waitingTime();
        click(settingPage.addNode_btn);
        waitingTime();
        String nodeNameStr = "测试添加节点";
        Helper.clickAndClearAndInput(settingPage.nodeName_input, nodeNameStr);
        String fullNodeInput = "https://www.baidu.com";
        Helper.clickAndClearAndInput(settingPage.fullNode_input, fullNodeInput);
        String eventServerInput = "http://www.yahu.com";
        Helper.clickAndClearAndInput(settingPage.eventServer_input, eventServerInput);
        click(settingPage.addCustomNode_btn);
        String nodeName = Helper.getNodeName(settingPage.node_list);
        Assert.assertEquals(nodeNameStr, nodeName);
        String fullNodeStr = Helper.getFullNodeStr(settingPage.node_list);
        Assert.assertEquals(fullNodeInput, fullNodeStr);
        String eventServerStr = Helper.getEventServerStr(settingPage.node_list);
        Assert.assertEquals(eventServerInput, eventServerStr);
    }

    @Test(
            groups = {"P1"},
            description = "Delete node success.",
            alwaysRun = true,
            enabled = true)
    public void test003deleteNodeTest() throws Exception {

        MainPage mainPage = new MainPage(DRIVER);
        SettingPage settingPage = new SettingPage(DRIVER);
        waitingTime(5);
        click(mainPage.set_btn);
        waitingTime();
        click(settingPage.settingList.get(2));
        waitingTime();
        click(settingPage.addNode_btn);
        String nodeNameStr = "测试添加节点";
        waitingTime();
        Helper.clickAndClearAndInput(settingPage.nodeName_input, nodeNameStr);
        String fullNodeInput = "https://www.baidu.com";
        Helper.clickAndClearAndInput(settingPage.fullNode_input, fullNodeInput);
        String eventServerInput = "http://www.yahu.com";
        Helper.clickAndClearAndInput(settingPage.eventServer_input, eventServerInput);
        click(settingPage.addCustomNode_btn);
        String nodeName = Helper.getNodeName(settingPage.node_list);
        Assert.assertEquals(nodeNameStr, nodeName);
        String fullNodeStr = Helper.getFullNodeStr(settingPage.node_list);
        Assert.assertEquals(fullNodeInput, fullNodeStr);
        String eventServerStr = Helper.getEventServerStr(settingPage.node_list);
        Assert.assertEquals(eventServerInput, eventServerStr);
        waitingTime();
        int size = settingPage.node_list.size();
        WebElement r1 = settingPage.node_list.get(size - 1).findElement(By.className("r1"));
        click(r1.findElement(By.className("delete")));
        click(settingPage.deleteConfirm_btn);
        nodeName = Helper.getNodeName(settingPage.node_list);
        log("nodeName:" + nodeName);
        Assert.assertNotEquals(nodeNameStr, nodeName);
    }

    @Test(
            groups = {"P1"},
            description = "Switch node success.",
            alwaysRun = true,
            enabled = true)
    public void test004switchNodeTest() throws Exception {

        MainPage mainPage = new MainPage(DRIVER);
        SettingPage settingPage = new SettingPage(DRIVER);
        waitingTime(5);
        click(mainPage.set_btn);
        waitingTime();
        click(settingPage.settingList.get(2));
        waitingTime();
        click(settingPage.addNode_btn);
        String nodeNameStr = "测试添加节点";
        waitingTime();
        Helper.clickAndClearAndInput(settingPage.nodeName_input, nodeNameStr);
        String fullNodeInput = "https://www.baidu.com";
        Helper.clickAndClearAndInput(settingPage.fullNode_input, fullNodeInput);
        String eventServerInput = "http://www.yahu.com";
        Helper.clickAndClearAndInput(settingPage.eventServer_input, eventServerInput);
        waitingTime(5);
        click(settingPage.addCustomNode_btn);
        waitingTime(5);
        int size = settingPage.node_list.size();
        WebElement r1 = settingPage.node_list.get(size - 1).findElement(By.className("r1"));
        waitingTime(5);
        click(r1);
        waitingTime();
        click(settingPage.back);
        waitingTime();
        click(mainPage.back_btn);
        mainPage.selectedChain_btn.click();
        waitingTime(2);
        // boolean isSelected = mainPage.chainList.get(6).getAttribute("class").contains("checked");
        boolean isSelected = isElementChecked(mainPage.chainList.get(6), "class", "checked");
        log("log:" + isSelected);
        Assert.assertTrue(isSelected);
    }

    @AfterMethod(enabled = true)
    public void after() throws Exception {
        logoutAccount();
    }
}
