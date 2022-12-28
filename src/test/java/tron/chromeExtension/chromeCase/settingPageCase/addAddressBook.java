package tron.chromeExtension.chromeCase.settingPageCase;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

public class addAddressBook extends Base {
  String addressName = "Thisnameisverylongdoyouknow";
  String addressInput = "TAJu6ArV39Fvn12bx4LqdG9GXxU2MYQW6k";
  String remarksInput = "第一次创建";
  String remarksInputSecond = "第一次修改";

  @BeforeMethod
  public void before() throws Exception {
    setUpChromeDriver();
    loginAccount(chainMain);
  }

  @Test(
      groups = {"P0"},
      description = "Add null name to address book failed.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test001addNullNameToAddressBookTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addAddressBook_btn);
    waitingTime();
    sendKeys(settingPage.name_input, "");
    clear(settingPage.address_input);
    sendKeys(settingPage.address_input, "TAJu6ArV39Fvn12bx4LqdG9GXxU2MYQW6k");
    sendKeys(settingPage.remarks_input, "自动化测试账户1的地址");
    click(settingPage.save_btn);
    waitingTime();
    Assert.assertTrue(Helper.isElementExist("primary", "保存"));
  }

  @Test(
      groups = {"P0"},
      description = "Add long name to address book failed.",
      alwaysRun = true,
      enabled = true)
  public void test002addLongNameToAddressBookTest() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    // settingPage.addressBook_btn
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addAddressBook_btn);
    waitingTime();
    sendKeys(settingPage.name_input, addressName);
    sendKeys(settingPage.address_input, addressInput);
    sendKeys(settingPage.remarks_input, remarksInput);
    click(settingPage.save_btn);
    waitingTime(5);
    String addressNameFromPage = getText(settingPage.addressName_text);
    log("addressNameFromPage：" + addressNameFromPage);
    Assert.assertEquals(addressName.substring(0, 20), addressNameFromPage);
    String address = getText(settingPage.address_text);
    log("address：" + address);
    Assert.assertEquals(addressInput, address);
    String remarks = getText(settingPage.remarks_text);
    log("remarks：" + remarks);
    Assert.assertEquals("备注：" + remarksInput, remarks);
  }

  @Test(
      groups = {"P0"},
      description = "Modify address name  to address book",
      alwaysRun = true,
      enabled = true)
  public void test003modifyAddressNameToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    // addressBook_btn
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addressRecord);
    waitingTime();
    Helper.clickAndClearAndInput(settingPage.modifyName_input, addressBookName);
    click(settingPage.save_btn);
    waitingTime(5);
    String addressNameFromPage = getText(settingPage.addressName_text);
    log("addressNameFromPage：" + addressNameFromPage);
    Assert.assertEquals(addressBookName, addressNameFromPage);
    String address = getText(settingPage.address_text);
    log("address：" + address);
    Assert.assertEquals(addressInput, address);
    String remarks = getText(settingPage.remarks_text);
    log("remarks：" + remarks);
    Assert.assertEquals("备注：" + remarksInput, remarks);
  }

  @Test(
      groups = {"P0"},
      description = "Modify address name  to address book",
      alwaysRun = true,
      enabled = true)
  public void test004modifyAddressToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    // addressBook_btn
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addressRecord);
    waitingTime();
    Helper.clickAndClearAndInput(settingPage.modifyAddress_input, loginAddress);
    click(settingPage.save_btn);
    waitingTime(5);
    String addressNameFromPage = getText(settingPage.addressName_text);
    log("addressNameFromPage：" + addressNameFromPage);
    Assert.assertEquals(addressBookName, addressNameFromPage);
    String address = getText(settingPage.address_text);
    log("address：" + address);
    Assert.assertEquals(loginAddress, address);
    String remarks = getText(settingPage.remarks_text);
    log("remarks：" + remarks);
    Assert.assertEquals("备注：" + remarksInput, remarks);
  }

  @Test(
      groups = {"P0"},
      description = "Modify address name  to address book",
      alwaysRun = true,
      enabled = true)
  public void test005modifyRemarksToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addressRecord);
    waitingTime();
    Helper.clickAndClearAndInput(settingPage.modifyRemarks_text, remarksInputSecond);
    click(settingPage.save_btn);
    waitingTime(5);
    String addressNameFromPage = getText(settingPage.addressName_text);
    log("addressNameFromPage：" + addressNameFromPage);
    Assert.assertEquals(addressBookName, addressNameFromPage);
    String address = getText(settingPage.address_text);
    log("address：" + address);
    Assert.assertEquals(loginAddress, address);
    String remarks = getText(settingPage.remarks_text);
    log("remarks：" + remarks);
    Assert.assertEquals("备注：" + remarksInputSecond, remarks);
  }

  @Test(
      groups = {"P0"},
      description = "Add the same name to address book.",
      alwaysRun = true,
      enabled = true,
      retryAnalyzer = MyIRetryAnalyzer.class)
  public void test006addTheSameToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(0));
    waitingTime();
    click(settingPage.addAddressBook_btn);
    waitingTime();
    sendKeys(settingPage.name_input, addressBookName);
    String tips = getText(settingPage.duplicateNameTips);
    log("tips:" + tips);
    Assert.assertEquals("名称已存在", tips);
    click(settingPage.close_btn);
    waitingTime(5);
    Assert.assertEquals("地址本", getText(settingPage.addressBook_content));
  }

  @Test(
      groups = {"P0"},
      description = "Search account to address book",
      alwaysRun = true,
      enabled = true)
  public void test007searchAccountToAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    // settingPage.addressBook_btn
    click(settingPage.settingList.get(0));
    waitingTime();
    sendKeys(settingPage.search_input, addressBookName);
    waitingTime();
    Assert.assertEquals(addressBookName, getText(settingPage.searchResultName_content));
    Assert.assertEquals(loginAddress, getText(settingPage.searchResultAddress_content));
    Assert.assertEquals(
        getText(settingPage.searchResultRemarks_content), "备注：" + remarksInputSecond);
  }

  @Test(
      groups = {"P0"},
      description = "Search account to address book",
      alwaysRun = true,
      enabled = true)
  public void test008deleteAccountFromAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    waitingTime(5);
    click(mainPage.set_btn);
    waitingTime();
    // settingPage.addressBook_btn
    click(settingPage.settingList.get(0));
    waitingTime();
    sendKeys(settingPage.search_input, addressBookName);
    waitingTime();
    click(settingPage.searchResultName_content);
    waitingTime();
    click(settingPage.delete_btn);
    waitingTime();
    click(settingPage.confirm_btn);
    waitingTime();
    sendKeys(settingPage.search_input, addressBookName);
    waitingTime(5);
    Assert.assertEquals("未找到该地址", getText(settingPage.tips_content));
  }

  @Test(
      groups = {"P0"},
      description = "Search account to address book",
      alwaysRun = true,
      enabled = true)
  public void test009import100AddressAccountFromAddressBookTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    SettingPage settingPage = new SettingPage(DRIVER);
    String[] addressList = {
      "TARUVSxQw79V93kXCSpoK1uQifisnTjP57",
      "TVjovxe1aPUL2Pdk1Zb63Ki8BSbtkuwGke",
      "TBVwURejdcbXn8YCucMfc87SoniRdFQPeM",
      "TCgr7JJyntBSdEjXxEbEcr6oZF7aVSojnV",
      "TXL135QqJPEGnRNMyYLJpsgwTeTw1q9qdv",
      "TGeqk2793cXBAfH3ZgmoQQGS8qsH2FdbzE",
      "TVk4G3DZJpi7E78Qm9oGpm4S37wSJBVLVt",
      "TG6EvUKAm6jw3P1xb17UvBveWdyyzjtdT4",
      "TWS6ikopmP4qTJ3iyjmXkMJvX5KicYG1JH",
      "TDqpyoF319MmSKRLz5HCJkoY6nt6FovyQE",
      "TPjxipBEePZEVkhGnWyGNzLFJWsdDEd7FR",
      "TXcG7jnNy5E9FUqjyA2ih3o7c8ivW2Zfwv",
      "TKEWms3NBHU3E199avFuSHVbqhsABsRRHy",
      "TKBfCF7ke12tE5KRRGDN8e3DVyaeEM9qG3",
      "TUg5mFSpS3qHRaaLcuFJAX9Dz4QYbtkrBR",
      "TAMGiqv4HYNQDaMYVnr53nHPc7K4CjnbTM",
      "TC1zx9nJJvyPCZNxEwk75yNSGMzhbAfuvi",
      "TLyTBmRy7ntDPbcupbZKG2Fa4gZPyuGPsj",
      "TTGf3E9u6ntTLDhW2LcFar6CcooBAv28k9",
      "TTmQVChQ46MADVVAcH95rABceHs28EUBVQ",
      "TUPwBcNB34P7Z9Uyh5ABHEhdh4BDvF5LWe",
      "TB75VfYSSgbEUs9SQUkrNiciE5JTSAwahp",
      "TLDCj8AJrjV2rUfDKmQ9U552pFYHPXSSDB",
      "TP8FKN8LaBg7c69q99J4YrPQcDcNFK71DV",
      "TRWB7svWWYsXCxP7g3CogbNqVj3sPTSfiQ",
      "TCi3X8o6t21kfATzzaU2s7Eub1g3Z9ady7",
      "TEkVeFZhD6qdzSdPCBsCRtMdvCj2EUfETP",
      "TThdhRLpfveSnQsxqtHpsAUM8NiGFdhejv",
      "TKDB3vyqSbc8AqHjDYxVi6KG4qbaQJ7Z1x",
      "TYfKkKSwbh9rMqFnQw5bHSWySW86spvs63",
      "TGjmSCduLsQkjUcwLQQQ4J1xXAp1bG2pSh",
      "TMsrY8K5honJVrqHqjJ4sYr4zTBEV18wr3",
      "TVGL8Gy7SFGtQZVUy5DAjn44BDeHpHK62D",
      "TMYb4CToK5vhzBzs66bmZftJoQyeYxjtkg",
      "TBLNKTczmDg8yistUeRwkD8XxJtxAMgnZc",
      "TLQz7bmfmahK21fYizHcuP8KQC4su5f8ER",
      "TA6nPRhcQL3JbA4gACH3AHV6TGzgk6Ldbo",
      "TGm8yGsELnAbE4WwSQHuEncQMu1xPdiGGu",
      "TRKH94esbHQLDA7q6Uhsg9fQ2Y7btQXHdC",
      "TUvDadhT4TzfSk9b5ihQRJ5HEzB3bZtQNE",
      "TEq5mdC8Wif21W3zCvSUHj9t3t4wbJ8XcQ",
      "TL11zUHhWraRxdghbMzdzktDvKkaEwMskA",
      "TGrVJcntyqR26VpnH5KHSgyxKCmoVS488J",
      "TMEpSnqVetZ4DM2Gj5LyuJemLZSnsVtCew",
      "TJRMwXo8nMgLTXNxTaM6dboU3T8Rkb3YvQ",
      "TJPwCyC5YWyYWf6wM82YSfWkBEbYKRbwjq",
      "TY4iXnndeaRAFkj1UynVw3FUn1VANWZpaW",
      "TWZVTmV9XnKVMnMRhWJFi7WTBnLBnpbik2",
      "TWJxYgdi6u8TqX18KAYRrVGNAsaExfs9Hu",
      "TP6qXZMdWKZDDnZDQvSmMNYqF49Sde6Zq6",
      "TDsxhuxudr2D1aZeVYWpnZwKyBdhyV5RYx",
      "TUJi8LX5r4GJN5syY5NjaZpyEqkx71AG7P",
      "THM3t5S5iHGYTRpbjuynjrSNqttnmqe6kp",
      "TBs5JNgtxtUZxFiuUUdfkQQuq4DBjMNV9T",
      "TRQCiPcxGeL6s6wz9KNuZrjXcpJbG6nNVU",
      "THRRHzTjvTae1Wdoq8RpQBBksstjggJWWC",
      "TKVExeemjCcok4oScgrRADsNjWg9ygm3Yy",
      "TGTeMVcX2QwachKpzeDNybbvG38Q98zbXH",
      "TPZeqfjnqV3yRMTNPebxkmDec3UFVRdZ6H",
      "TYGbT54zJGLVwc9iHBFKHbYzXR4okitxsP",
      "TUADYFYRo5gFQJxBR9W7F5vQASoKP1jNE5",
      "TC3pYLshCMh5DUr5jkMnb4VkP1m2Pu6JR7",
      "TAdPWSmRM7EBGt9PD6fxZxxaxYemLv8zhZ",
      "TXdzDMaYxmvmDSRVHczyrydUdZJXC5acmf",
      "TAcWDZ14AbR8QPT5cq2TapUtMA8ZFFaoNo",
      "TLYRqC91ReEhYqTMPtDXm2SPmUsQx3Y2pb",
      "TWMxdErRzwbUAngcpN9PuYH7fp8KCCYv85",
      "TS6CrcLh2Sip9whbFm4nushmP6xfbx1wdA",
      "TVKwGGfCdmmareRLk8TYBzKgQc6p8HUhNU",
      "TAoMTaPb7JzBpHCKrSTKu1J2cXLVnDnVoM",
      "TK2KyL4SybKzSZu6LN4Cf3yprL3YcbTBjK",
      "TNMLPgPafuR8S8fmaHENEhCbS1DuNYEtKu",
      "TTJaFydGfhHjp674Bnz8Zsa2qoJ9JX8azw",
      "TYGHkj8ao45ao9k1EUnaJZux5mD4GFNh47",
      "TZ3akkh7M8HxvverM8mBVSaiNa8P11tYbE",
      "TASgJ41LDsFUSkqCrH8BPm3uEdd4Sdqsya",
      "TPWL6KoatYFRrWxwUe13thggvJ3PsZagWe",
      "TQJBsJomPxVHCMg4uC5kLgnpqpoCLcf8ix",
      "TU5oxk6MgjUw6ihicvNQNSdUp6Ned1wFMN",
      "TM5oADYvU6wFu8RdoWAP1iM6nnhVKFr7iW",
      "TWwe9TrctaX5D23EhnmfdSgZF6wjfZNg4g",
      "TJPjkXBhptXR8GSD2coNVrigfZtvy1S8x8",
      "TAP72HcL9FihZ94uE53g86R3GUpHkLNGts",
      "TG6F5LvKZuFRz77NmHq162QwTsJeMA8cSt",
      "TG68YidfKrJ5yiuLFHcy8s4LTFnqzkV9gv",
      "TWRLevdLAGMXB826h9KmRF6o5mZ5Qn3FSk",
      "TASWU5Ug7Vi67fRTHTKU1VcAbngRjsRddb",
      "TMFcPsnrnxKfpLWbVaRfQGk4qwC7Ln1V5H",
      "TTvvbwhWxLsbVhPG9sWFLvRbEwsZDLCmqW",
      "TCpQH77no2h21YmPafgU6YMjXmg7Bctevv",
      "TB9kpNepQhEHMgtX797fUmQeYPKtbwxmTp",
      "TKewufiBUJ83zP7jM3D2qevsjEbs41DR9Z",
      "TCYWMzT13Ve9zJPSPVkoKFULUx7MqcDVg1",
      "TKXcYDbVqBX6QDqJ29sa1VFngp9a4UBXVD",
      "TSb71QnEr41x22WCeBNDgkT8C7M11JLMVU",
      "TCF3rJLjSXy4VqGUZpCRvbew8MH23M2Xto",
      "TTutqbuutKXTKCpggGcp5XAGh5YMGkVdJ2",
      "TGvmyP9kvvbFDD7tbqtQFfh8T7vtGAgDeG",
      "TRkqdzx5tSFws4oriPhGSoh3qJAbQfwRfK",
      "THMtHkvWw8AaMYxynMe9PeruRwHS7ehr61",
      "TFDHbg6fpr2rXSqXa53WK5VwuaiJHZc3GG",
    };

    click(mainPage.set_btn);
    waitingTime();
    click(settingPage.settingList.get(0));
    waitingTime();

    for (String i : addressList) {
      click(settingPage.addAddressBook_btn);
      waitingTime();
      sendKeys(settingPage.name_input, i);
      clear(settingPage.address_input);
      sendKeys(settingPage.address_input, i);
      sendKeys(settingPage.remarks_input, "自动化测试账户1的地址"+i+i+i+i+i+i);
      click(settingPage.save_btn);
      waitingTime();
     // Assert.assertTrue(Helper.isElementExist("primary", "保存"));
    }
  }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
