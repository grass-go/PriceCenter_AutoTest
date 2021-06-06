package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import tron.common.utils.CompareJson;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;

public class allCollections extends TronlinkBase {
  private String responseString;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();

  //返回我的全部收藏品列表，包括置顶token、用户关注、用户持有。
  //验证点：1. 一定包含置顶的token, 目前只有TNFT
  //        2. 一定包含关注未持有的token
  //        3. 一定包含关注持有的token
  //        4. 所有token的排序问题，全文对比可覆盖

  @SneakyThrows
  @Test( enabled = false)
  public void allCollectionsTest001(){
    //read expected json
    char cbuf[] = new char[2000];
    InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/allCollections_exp.json")),"UTF-8");
    int len = 0;
    try {
      len = input.read(cbuf);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String expResponse =new String(cbuf,0,len);


    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","Fe2iJhHiitdRlqufajemkuvf2CA%3D");
    params.put("address",address721_Hex);

    response = TronlinkApiList.v2AllCollections(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseString = TronlinkApiList.parseResponse2String(response);
    String cmp_result = new CompareJson("contractAddress").compareJson(responseString, expResponse);
    System.out.println("=========actual response========== "+responseString+"\n");
    System.out.println("=========expect response========== "+expResponse+"\n");
    System.out.println("=========cmp_result=============== "+cmp_result);
    Assert.assertEquals("null",cmp_result);

  }

}
