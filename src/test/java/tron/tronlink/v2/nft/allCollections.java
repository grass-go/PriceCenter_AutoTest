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
  @Test( enabled = true)
  public void allCollectionsTestManual(){
    params.clear();
    params.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");

    response = TronlinkApiList.v2AllCollections(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseString = TronlinkApiList.parseResponse2String(response);


  }

}
