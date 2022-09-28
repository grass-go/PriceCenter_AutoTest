package tron.tronlink.old;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

@Slf4j
public class Upgrade extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;
  private JSONObject object;

  private JSONArray array = new JSONArray();
  JSONObject jsonObject = new JSONObject();
  List<String> trc10tokenList = new ArrayList<>();
  Map<String, String> params = new HashMap<>();

  @Test(enabled = true, groups={"NoSignature"})
  public void UpgradeForAndroidByV2LowVersionWithNoSig() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("System","Android");
    params.put("DeviceID", "1111111111");
    params.put("Version","3.7.0");
    //params.put("DownloadPlatform", "appstore");
    params.put("Lang","1");
    params.put("channel","official");
    params.put("packageName", "com.tronlinkpro.wallet");
    params.put("chain","MainChain");
    response = TronlinkApiList.v1UpgradeV2NoSig(params);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonObjectContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    System.out.println("newVersion:" + newVersion);
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }

  @Test(enabled = true)
  public void UpgradeForAndroidByV2HighVersionWithNoSig() throws Exception {
    Map<String, String> header = new HashMap<>();
    header.put("Version",TronlinkApiList.androidUpdateVersion);
    header.put("System", TronlinkApiList.defaultSys);
    response = TronlinkApiList.v1UpgradeV2NoSig(header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true)
  public void test01UpgradeForAndroidByV2() throws Exception {
    Map<String, String> header = new HashMap<>();
    header.put("System","Android");
    header.put("DeviceID", "1111111111");
    header.put("Version","3.7.0");
    //params.put("DownloadPlatform", "appstore");
    header.put("Lang","1");
    header.put("channel","official");
    header.put("packageName", "com.tronlinkpro.wallet");
    header.put("chain","MainChain");

    Map<String, String> params = new HashMap<>();
    params.put("address",quince_B58);
    response = TronlinkApiList.v1UpgradeV2(params,header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonObjectContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    System.out.println("newVersion:" + newVersion);
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }

  @Test(enabled = true)
  public void UpgradeForAndroidByV1LowVerisonWithNoSig() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("System","Android");
    params.put("DeviceID", "1111111111");
    params.put("Version","3.7.0");
    //params.put("DownloadPlatform", "appstore");
    params.put("Lang","1");
    params.put("channel","official");
    params.put("packageName", "com.tronlinkpro.wallet");
    params.put("chain","MainChain");
    response = TronlinkApiList.v1UpgradeNoSig(params);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonObjectContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }

  @Test(enabled = true)
  public void UpgradeForAndroidByV1HighVersionWithNoSig() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("System","Android");
    params.put("DeviceID", "1111111111");
    params.put("Version","4.12.0");
    params.put("Lang","1");
    params.put("channel","official");
    params.put("packageName", "com.tronlinkpro.wallet");
    params.put("chain","MainChain");
    response = TronlinkApiList.v1UpgradeNoSig(params);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true)
  public void test02UpgradeForAndroidByV1() throws Exception {
    Map<String, String> header = new HashMap<>();
    header.put("System","Android");
    header.put("DeviceID", "1111111111");
    header.put("Version","3.7.0");
    //header.put("DownloadPlatform", "appstore");
    header.put("Lang","1");
    header.put("channel","official");
    header.put("packageName", "com.tronlinkpro.wallet");
    header.put("chain","MainChain");
    Map<String, String> params = new HashMap<>();
    params.put("address",quince_B58);
    response = TronlinkApiList.v1Upgrade(params,header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonObjectContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }

  @Test(enabled = true)
  public void test03UpgradeForIOSByV2() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("address",quince_B58);
    Map<String, String> header = new HashMap<>();
    header.put("Version","3.7.0");
    header.put("DownloadPlatform", "appstore");
    header.put("System", "iOS");
    response = TronlinkApiList.v1UpgradeV2(params,header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonObjectContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    System.out.println("newVersion:" + newVersion);
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }

  @Test(enabled = true)
  public void test04UpgradeForIOSByV1() throws Exception {
    Map<String, String> header = new HashMap<>();
    header.put("System","iOS");
    header.put("DeviceID", "1111111111");
    header.put("Version","3.7.0");
    header.put("DownloadPlatform", "appstore");
    header.put("Lang","1");
    header.put("channel","official");
    header.put("packageName", "com.tronlinkpro.wallet");
    header.put("chain","MainChain");
    Map<String, String> params = new HashMap<>();
    params.put("address",quince_B58);
    response = TronlinkApiList.v1Upgrade(params,header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonObjectContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }

  @Test(enabled = true)
  public void test05UpgradeForHarmonyByV2() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("address",quince_B58);
    Map<String, String> header = new HashMap<>();
    header.put("Version","4.1.0");
    params.put("packageName", "wallet.tronlink.harmony");
    header.put("System", "Android");
    response = TronlinkApiList.v1UpgradeV2(params,header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonObjectContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    System.out.println("newVersion:" + newVersion);
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }
}
