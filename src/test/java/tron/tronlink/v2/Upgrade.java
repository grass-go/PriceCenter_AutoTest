package tron.tronlink.v2;

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



  @Test(enabled = true)
  public void test01UpgradeForAndroidByV2() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("System","Android");
    params.put("DeviceID", "1111111111");
    params.put("Version","3.7.0");
    //params.put("DownloadPlatform", "appstore");
    params.put("Lang","1");
    params.put("channel","official");
    params.put("packageName", "com.tronlinkpro.wallet");
    params.put("chain","MainChain");
    response = TronlinkApiList.v2Upgrade(params);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    System.out.println("newVersion:" + newVersion);
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }

  @Test(enabled = true)
  public void test02UpgradeForAndroidByV1() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("System","Android");
    params.put("DeviceID", "1111111111");
    params.put("Version","3.7.0");
    //params.put("DownloadPlatform", "appstore");
    params.put("Lang","1");
    params.put("channel","official");
    params.put("packageName", "com.tronlinkpro.wallet");
    params.put("chain","MainChain");
    response = TronlinkApiList.v1Upgrade(params);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);


  }


  @Test(enabled = true)
  public void test03UpgradeForIOSByV2() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("System","iOS");
    params.put("DeviceID", "1111111111");
    params.put("Version","3.7.0");
    params.put("DownloadPlatform", "appstore");
    params.put("Lang","1");
    params.put("channel","official");
    params.put("packageName", "com.tronlinkpro.wallet");
    params.put("chain","MainChain");
    response = TronlinkApiList.v2Upgrade(params);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    System.out.println("newVersion:" + newVersion);
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }

  @Test(enabled = true)
  public void test04UpgradeForIOSByV1() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("System","iOS");
    params.put("DeviceID", "1111111111");
    params.put("Version","3.7.0");
    params.put("DownloadPlatform", "appstore");
    params.put("Lang","1");
    params.put("channel","official");
    params.put("packageName", "com.tronlinkpro.wallet");
    params.put("chain","MainChain");
    response = TronlinkApiList.v1Upgrade(params);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }

  @Test(enabled = true)
  public void test05UpgradeForHarmonyByV2() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("System","Android");
    params.put("DeviceID", "1111111111");
    params.put("Version","3.7.0");
    params.put("Lang","1");
    params.put("packageName", "wallet.tronlink.harmony");
    params.put("chain","MainChain");
    response = TronlinkApiList.v2Upgrade(params);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject upgradeObject = responseContent.getJSONObject("data");
    TronlinkApiList.printJsonContent(responseContent.getJSONObject("data"));
    String newVersion = upgradeObject.getString("title");
    System.out.println("newVersion:" + newVersion);
    newVersion = newVersion.split("V")[1];
    newVersion = newVersion.replace(".","");
    Assert.assertTrue(Integer.valueOf(newVersion) > 400);
  }
}
