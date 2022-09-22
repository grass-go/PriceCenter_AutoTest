package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class transactionHistory extends TronlinkBase {
  private HttpResponse response;
  private String defaultHash = "bf1da4cac95c7edc0b219fed147460fc32eacc47736728a70710a76d8ce0bec2";

  @Test(enabled = true, description = "Api GET /api/simple-transaction test")
  public void test001TransactionTo() throws Exception {
    HashMap<String,String> parameter = new HashMap<>();
    parameter.put("to", "TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of");

    int index;
    for(index=0; index<10; index++) {
      log.info("test001TransactionTo cur index is "+ index);

      try {
        response = api.transactionHistory(parameter);
      }catch (Exception e){
        Thread.sleep(1000);
        continue;
      }

      JSONObject jsonObject = api.parseResponse2JsonObject(response);
      api.printJsonObjectContent(jsonObject);

      if (jsonObject.toString().equals("{}") ) {
        Thread.sleep(1000);
        continue;
      }
      else {
        index=11;
      }
      JSONArray data = jsonObject.getJSONArray("data");
      Assert.assertNotEquals(data, null);
      for (Object object:data){
        JSONObject history = (JSONObject) JSON.toJSON(object);
        Assert.assertTrue(!history.getString("hash").isEmpty());
        Assert.assertTrue(!history.getString("timestamp").isEmpty());
        Assert.assertTrue(!history.getString("ownerAddress").isEmpty());
        Assert.assertTrue(!history.getString("toAddress").isEmpty());
        Assert.assertTrue(!history.getString("contractType").isEmpty());
  //      String toAddress = history.getString("toAddress");
  //      Assert.assertEquals("TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of",toAddress);
        String hash = history.getString("hash");

        if(defaultHash.equals(hash)){
          JSONObject contractData = history.getJSONObject("contractData");
          Assert.assertEquals("10000000",contractData.getString("amount"));
          Assert.assertEquals("1002636",contractData.getString("asset_name"));
          Assert.assertEquals("THvoHCwueb9WHMdiVk5M2kYd9HUKVdh6wv",contractData.getString("owner_address"));
          Assert.assertEquals("TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of",contractData.getString("to_address"));
        }
      }
    }
    Assert.assertEquals(12,index);

  }

  @Test(enabled = true)
  public void test002TransactionFrom() throws Exception {
        HashMap<String, String> parameter = new HashMap<>();
        parameter.put("from", "TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of");

        int index;
        for(index=0; index<10; index++) {
          log.info("test002TransactionFrom cur index is "+ index);

          try {
            response = api.transactionHistory(parameter);
          }catch (Exception e){
            Thread.sleep(1000);
            continue;
          }
          JSONObject jsonObject = api.parseResponse2JsonObject(response);
          api.printJsonObjectContent(jsonObject);
          if (jsonObject.toString().equals("{}") ) {
            Thread.sleep(1000);
            continue;
          }
          else {
            index=11;
          }
          JSONArray data = jsonObject.getJSONArray("data");
          for (Object object : data) {
            JSONObject history = (JSONObject) JSON.toJSON(object);
            Assert.assertTrue(!history.getString("hash").isEmpty());
            Assert.assertTrue(!history.getString("timestamp").isEmpty());
            Assert.assertTrue(!history.getString("ownerAddress").isEmpty());
            //     Assert.assertTrue(!history.getString("toAddress").isEmpty());
            Assert.assertTrue(!history.getString("contractType").isEmpty());
            Assert.assertEquals(history.getString("ownerAddress"), "TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of");
          }
        }
    Assert.assertEquals(12,index);
  }

  @Test(enabled = true)
  public void test003TransactionAll() throws Exception {
    HashMap<String, String> parameter = new HashMap<>();
    parameter.put("address", "TMNQnpTsNHuK1NwqMf6WTBydXvNsv9p6of");

    int index;
    JSONObject jsonObject;
    for(index=0; index<10; index++) {
      log.info("test003TransactionAll cur index is "+ index);
      try {
        response = api.transactionHistory(parameter);
      }catch (Exception e){
        Thread.sleep(1000);
        continue;
      }
      index = 11;
      jsonObject = api.parseResponse2JsonObject(response);
      api.printJsonObjectContent(jsonObject);
      if (jsonObject.toString().equals("{}") ) {
        Thread.sleep(1000);
        continue;
      }
      else {
        index=11;
      }
      JSONArray data = jsonObject.getJSONArray("data");

      for (Object object : data) {
        JSONObject history = (JSONObject) JSON.toJSON(object);
        Assert.assertTrue(!history.getString("hash").isEmpty());
        Assert.assertTrue(!history.getString("timestamp").isEmpty());
        Assert.assertTrue(!history.getString("ownerAddress").isEmpty());
        //      Assert.assertTrue(!history.getString("toAddress").isEmpty());
        Assert.assertTrue(!history.getString("contractType").isEmpty());
      }
    }
    Assert.assertEquals(12,index);
  }

}

