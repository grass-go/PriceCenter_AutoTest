package tron.chromeExtension.chromeCase.settingPageCase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.pages.MainPage;
import tron.chromeExtension.pages.SettingPage;
import tron.chromeExtension.utils.Helper;
import tron.common.utils.MyIRetryAnalyzer;

public class Add100AddressTest extends Base {
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
    waitingTime();
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
      sendKeys(settingPage.remarks_input, "自动化测试账户1的地址" + i + i + i + i + i + i);
      click(settingPage.save_btn);
      waitingTime();
      // Assert.assertTrue(Helper.isElementExist("primary", "保存"));
    }
  }

  @Test(
      groups = {"P0"},
      description = "Search account to address book",
      alwaysRun = true,
      enabled = true)
  public void test0010importSiYaoTest() throws Exception {

    MainPage mainPage = new MainPage(DRIVER);
    // SettingPage settingPage = new SettingPage(DRIVER);
    String[] addressList = {
      "4930c2716f219355ff029e8ffcd0e7fe53983d823f921574088eebfa7cf55c62",
      "cd81619c274c7dad60b6cd5debec88d53709690f18cbb3650f374c0941e44a70",
      "b32E71eab2f7b3057cde9ea6a489a404b7e0f09320a86671f3addcf1581979f5",
      "2e5be8e517db06cea305ee3b7fe52f6e12578884bd640435019a774980c1212a",
      "99130b64d565cecd68053b8021e4de81c55928f0ae95e11638e00c9230e58e93",
      "0cc3ecff51e34c700279148558de4401f2245b2a825b7b53341581b8db3a08a7",
      "ab4f4b7710e85847c273e4faeec6ff5409af9b122edd8e45e842c598e4c28a57",
      "25ddf550971a85d2f49e1acd0e6d54484a341555da6cf95095a38f8ff55881ec",
      "b671b550267d61e87d5451a4d65032f74fcb5e993750ab4af9577a37dde22504",
      "dad551f8742aa68ae8ea8d69e2f09eaea519f698c7562760760015b7fa5f9596"
    };

    for (String i : addressList) {
      click(mainPage.plus);
      waitingTime(5);
      click(mainPage.importWallet);
      waitingTime();
      sendKeys(mainPage.ZhuJiCi_text, i);
      click(mainPage.nextStep_btn);
      waitingTime();
      click(mainPage.importSiYao);

      // Assert.assertTrue(Helper.isElementExist("primary", "保存"));
    }
  }

    @Test(
        groups = {"P0"},
        description = "Search account to address book",
        alwaysRun = true,
        enabled = true)
    public void test0011importZhuJiCiTest() throws Exception {

        MainPage mainPage = new MainPage(DRIVER);
        // SettingPage settingPage = new SettingPage(DRIVER);
        String[] addressList = {
            "library unaware rocket green resource solar sock little host luggage elite unveil",
            "photo seven spend ski barely food slow mistake flee habit insect issue",
            "duck clip carbon agree begin gadget dish opera garden power small border",
            "march section must private hint federal indoor canoe thrive garment funny space",
            "place regret surprise chimney phrase morning quick better size employ absurd actor",
            "logic thrive tooth unlock image place silk afraid stairs banana crane paper",
            "risk forum party gentle milk oval lizard utility merry define control update",
            "absent wing execute display half feel mansion pole crystal work innocent barely",
            "canoe meadow million recipe suffer verify limb click hour prevent point tennis",
            "dash kingdom online any excite flash flash truck click artefact champion soap"
        };

        for (String i : addressList) {
            click(mainPage.plus);
            waitingTime(5);
            click(mainPage.importWallet);
            waitingTime();
            sendKeys(mainPage.ZhuJiCi_text, i);
            click(mainPage.nextStep_btn);
            waitingTime();
            click(mainPage.importSiYao);
            waitingTime();
            click(mainPage.importConfirm);

            // Assert.assertTrue(Helper.isElementExist("primary", "保存"));
        }
    }

  @AfterMethod(enabled = true)
  public void after() throws Exception {
    logoutAccount();
  }
}
