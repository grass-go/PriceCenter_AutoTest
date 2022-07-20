package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.math.BigDecimal;

@Slf4j
public class ScanNode {
  private JSONObject responseContent;

  private HttpResponse response;

  @Test(enabled = false, description = "v1 scan node test")
  public void test000ScanNode() throws Exception{
    JSONObject job = new JSONObject();
    for(int i=0;i<3;i++){
      String url = "https://list.tronlink.org/api/wallet/blockNum";
      response = TronlinkApiList.createGetConnect(url);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      responseContent = TronlinkApiList.parseJsonObResponseContent(response);
      JSONArray dataArray = responseContent.getJSONArray("data");
      double scanNum = dataArray.getJSONObject(0).getDoubleValue("number");


      //url = "http://34.220.77.106:8090:8090/wallet/getnowblock";
      url = "https://api.trongrid.io/wallet/getnowblock";
      response = TronlinkApiList.createGetConnect(url);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      responseContent = TronlinkApiList.parseJsonObResponseContent(response);
      JSONObject dataArray1 = responseContent.getJSONObject("block_header").getJSONObject("raw_data");
      double chainNum = dataArray1.getDoubleValue("number");


      BigDecimal scan = new BigDecimal(scanNum);
      BigDecimal chain = new BigDecimal(chainNum);
      int sub = chain.subtract(scan).intValue();
      String str = "scanNum:"+scanNum+"    \n chainNum: "+chainNum+"\n sub:"+sub;
      Assert.assertTrue(sub <= 12);
      job.put(i+"",str);
      Thread.sleep(300);
    }
    System.out.println(job.toJSONString());
  }

  @Test(enabled = true, description = "v2 scan node test")
  public void test001ScanNode() throws Exception{
    JSONObject job = new JSONObject();
    for(int i=0;i<3;i++){
      String url = "http://3.130.220.30:8888//api/block/current_num";
      response = TronlinkApiList.createGetConnect(url);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      responseContent = TronlinkApiList.parseJsonObResponseContent(response);
      double customerTrxNum =responseContent.getDouble("customerTrxNum");
      double customerTrc20Num =responseContent.getDouble("customerTrc20Num");
      double productNum =responseContent.getDouble("productNum");
      double customerTrc10Num =responseContent.getDouble("customerTrc10Num");

      //url = "http://34.220.77.106:8090/wallet/getnowblock";
      url = "https://api.trongrid.io/wallet/getnowblock";
      response = TronlinkApiList.createGetConnect(url);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      responseContent = TronlinkApiList.parseJsonObResponseContent(response);
      JSONObject dataArray1 = responseContent.getJSONObject("block_header").getJSONObject("raw_data");
      double chainNum = dataArray1.getDoubleValue("number");


      BigDecimal trx = new BigDecimal(customerTrxNum);
      BigDecimal trc20 = new BigDecimal(customerTrc20Num);
      BigDecimal proNum = new BigDecimal(productNum);
      BigDecimal trc10 = new BigDecimal(customerTrc10Num);
      BigDecimal chain = new BigDecimal(chainNum);
      int subTrx = chain.subtract(trx).intValue();
      int subTrc20 = chain.subtract(trc20).intValue();
      int subProNum = chain.subtract(proNum).intValue();
      int subTrc10 = chain.subtract(trc10).intValue();

      String str = "subTrx:"+subTrx+"\n subTrc20: "+subTrc20+"\n subProNum:"+subProNum+"\n subTrc10:"+subTrc10;
      Assert.assertTrue(subTrx <= 20);
      Assert.assertTrue(subTrc20 <= 20);
      Assert.assertTrue(subProNum <= 20);
      Assert.assertTrue(subTrc10 <= 20);
      job.put(i+"",str);
      Thread.sleep(300);
    }


    log.info(job.toJSONString());
  }
}
