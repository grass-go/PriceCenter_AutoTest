package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.CompareJson;
import tron.tronlink.base.TronlinkBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class getDelegatedResource extends TronlinkBase {
    private JSONObject responseContent;
    private String responseString;
    private JSONObject object;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();

    //Check 给他人冻结的能量，排序为正序，使用58地址
    @SneakyThrows
    @Test(enabled = true)
    public void getDelegatedResource01(){
        char cbuf[] = new char[5000];
        InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/getDelegatedResource01_exp.json")),"UTF-8");
        int len =input.read(cbuf);
        String expResponse =new String(cbuf,0,len);

        params.clear();
        params.put("address",address721_B58);
        params.put("start", "0");
        params.put("limit", "10");
        params.put("type", "2");
        params.put("sourceType","2");
        params.put("reverse", "false");

        response = TronlinkApiList.getDelegatedResource(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        responseString = TronlinkApiList.parseResponse2String(response);

        String cmp_result = new CompareJson("data").compareJson(responseString, expResponse);
        System.out.println("=========actual response========== "+responseString+" ");
        System.out.println("=========expect response========== "+expResponse+" ");
        System.out.println("=========cmp_result=============== "+cmp_result);
        Assert.assertEquals("null",cmp_result);
    }

    //Check 给他人冻结的能量，排序为倒序，使用41地址
    @SneakyThrows
    @Test(enabled = true)
    public void getDelegatedResource02(){
        char cbuf[] = new char[5000];
        InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/getDelegatedResource02_exp.json")),"UTF-8");
        int len =input.read(cbuf);
        String expResponse =new String(cbuf,0,len);

        params.clear();
        params.put("address",address721_Hex);
        params.put("start", "0");
        params.put("limit", "10");
        params.put("type", "2");
        params.put("sourceType","2");
        params.put("reverse", "true");

        response = TronlinkApiList.getDelegatedResource(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        responseString = TronlinkApiList.parseResponse2String(response);

        String cmp_result = new CompareJson("data").compareJson(responseString, expResponse);
        System.out.println("=========actual response========== "+responseString+" ");
        System.out.println("=========expect response========== "+expResponse+" ");
        System.out.println("=========cmp_result=============== "+cmp_result);
        Assert.assertEquals("null",cmp_result);
    }
    //Check 给他人冻结的带宽，排序为正序，使用41地址
    @SneakyThrows
    @Test(enabled = true)
    public void getDelegatedResource03(){
        char cbuf[] = new char[5000];
        InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/getDelegatedResource03_exp.json")),"UTF-8");
        int len =input.read(cbuf);
        String expResponse =new String(cbuf,0,len);

        params.clear();
        params.put("address",address721_Hex);
        params.put("start", "0");
        params.put("limit", "10");
        params.put("type", "2");
        params.put("sourceType","1");

        response = TronlinkApiList.getDelegatedResource(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        responseString = TronlinkApiList.parseResponse2String(response);

        String cmp_result = new CompareJson("data").compareJson(responseString, expResponse);
        System.out.println("=========actual response========== "+responseString+" ");
        System.out.println("=========expect response========== "+expResponse+" ");
        System.out.println("=========cmp_result=============== "+cmp_result);
        Assert.assertEquals("null",cmp_result);
    }
    //Check 给他人冻结的带宽，排序为倒序，使用58地址
    @SneakyThrows
    @Test(enabled = true)
    public void getDelegatedResource04(){
        char cbuf[] = new char[5000];
        InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/getDelegatedResource04_exp.json")),"UTF-8");
        int len =input.read(cbuf);
        String expResponse =new String(cbuf,0,len);

        params.clear();
        params.put("address",address721_B58);
        params.put("start", "0");
        params.put("limit", "10");
        params.put("type", "2");
        params.put("sourceType","1");
        params.put("reverse", "true");

        response = TronlinkApiList.getDelegatedResource(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        responseString = TronlinkApiList.parseResponse2String(response);

        String cmp_result = new CompareJson("data").compareJson(responseString, expResponse);
        System.out.println("=========actual response========== "+responseString+" ");
        System.out.println("=========expect response========== "+expResponse+" ");
        System.out.println("=========cmp_result=============== "+cmp_result);
        Assert.assertEquals("null",cmp_result);
    }
    //Check 给他人冻结的所有资源，排序为正序，使用58地址
    @SneakyThrows
    @Test(enabled = true)
    public void getDelegatedResource05(){
        char cbuf[] = new char[5000];
        InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/getDelegatedResource05_exp.json")),"UTF-8");
        int len =input.read(cbuf);
        String expResponse =new String(cbuf,0,len);

        params.clear();
        params.put("address",address721_B58);
        params.put("start", "0");
        params.put("limit", "10");
        params.put("type", "2");
        params.put("sourceType","0");

        response = TronlinkApiList.getDelegatedResource(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        responseString = TronlinkApiList.parseResponse2String(response);

        String cmp_result = new CompareJson("data").compareJson(responseString, expResponse);
        System.out.println("=========actual response========== "+responseString+" ");
        System.out.println("=========expect response========== "+expResponse+" ");
        System.out.println("=========cmp_result=============== "+cmp_result);
        Assert.assertEquals("null",cmp_result);
    }
    //check 给他人冻结的所有资源，排序为倒序，使用41地址
    @SneakyThrows
    @Test(enabled = true)
    public void getDelegatedResource06(){
        char cbuf[] = new char[5000];
        InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/getDelegatedResource06_exp.json")),"UTF-8");
        int len =input.read(cbuf);
        String expResponse =new String(cbuf,0,len);

        params.clear();
        params.put("address",address721_Hex);
        params.put("start", "0");
        params.put("limit", "10");
        params.put("type", "2");
        params.put("sourceType","0");
        params.put("reverse", "true");

        response = TronlinkApiList.getDelegatedResource(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        responseString = TronlinkApiList.parseResponse2String(response);

        String cmp_result = new CompareJson("data").compareJson(responseString, expResponse);
        System.out.println("=========actual response========== "+responseString+" ");
        System.out.println("=========expect response========== "+expResponse+" ");
        System.out.println("=========cmp_result=============== "+cmp_result);
        Assert.assertEquals("null",cmp_result);
    }
    //check start=2， limit=2生效

}
