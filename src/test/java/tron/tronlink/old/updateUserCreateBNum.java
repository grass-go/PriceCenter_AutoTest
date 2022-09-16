package tron.tronlink.old;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

@Slf4j
public class updateUserCreateBNum extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();
  Map<String, String> headers = new HashMap<>();
  Long updateBlockNum = System.currentTimeMillis() % 1000;

  @Test(enabled = true)
  public void test01UpdateUserCreateBNum(){
    log.info("updateBlockNum: "  + updateBlockNum);
    params.put("userhash","aaaa");
    params.put("number",String.valueOf(updateBlockNum));
    params.put("access","access");
    headers.put("DownloadPlatform","list.tronlink.org");
    headers.put("Version","4.0.2");

    response = TronlinkApiList.v1UpdateUserCreateBNum(String.valueOf(updateBlockNum),"aaaa",headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    Assert.assertEquals(responseContent.getString("data"),"update successful");
  }

  @Test(enabled = true)
  public void test02UserCreateBlockNum(){
    params.put("userhash","aaaa");

    headers.put("DownloadPlatform","list.tronlink.org");
    headers.put("Version","4.0.2");
    response = TronlinkApiList.v1UserCreateBlockNum(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    log.info("responseContent.getJSONObject(\"data\").getLong(\"number\")" + responseContent.getJSONObject("data").getLong("number"));
    Assert.assertTrue(responseContent.getJSONObject("data").getLong("number") == -1L);
  }


}
