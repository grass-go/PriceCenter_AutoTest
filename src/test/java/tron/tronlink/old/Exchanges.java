package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Exchanges extends TronlinkBase {

    public static class Data {
        private String token;
        private String exchange;
        private String symbol;
        private String logo;
        private String decimal;
        private String name;
        private int delete;
        public void setToken(String token) {
            this.token = token;
        }
        public String getToken() {
            return token;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }
        public String getExchange() {
            return exchange;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
        public String getSymbol() {
            return symbol;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
        public String getLogo() {
            return logo;
        }

        public void setDecimal(String decimal) {
            this.decimal = decimal;
        }
        public String getDecimal() {
            return decimal;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setDelete(int delete) {
            this.delete = delete;
        }
        public int getDelete() {
            return delete;
        }

    }

    // 响应体
    public static class SwapExRsp {

        private int code;
        private String message;
        private List<Data> data;
        private String next;
        private int more;
        private String factoryAddr;
        private String aggregateAddr;
        public void setCode(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
        public List<Data> getData() {
            return data;
        }

        public void setNext(String next) {
            this.next = next;
        }
        public String getNext() {
            return next;
        }

        public void setMore(int more) {
            this.more = more;
        }
        public int getMore() {
            return more;
        }

        public void setFactoryAddr(String factoryAddr) {
            this.factoryAddr = factoryAddr;
        }
        public String getFactoryAddr() {
            return factoryAddr;
        }

        public void setAggregateAddr(String aggregateAddr) {
            this.aggregateAddr = aggregateAddr;
        }
        public String getAggregateAddr() {
            return aggregateAddr;
        }

    }



    private HttpResponse response;

    @Test(description = "交易对信息",groups = {"NoSignature"})
    public void Test_swap_exchanges_01LowVersionWithNoSig() {

        Map<String,String> params = new HashMap<String,String>();
        params.put("address",quince_B58);
        params.put("type", "1");
        response = TronlinkApiList.swapExchangesNoSig(params,null);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        String apStr = TronlinkApiList.parseResponse2String(response);
        log.info(apStr);
        SwapExRsp ser = JSONObject.parseObject(apStr, SwapExRsp.class);
        org.testng.Assert.assertEquals(ser.getCode(), 0);
        Assert.assertEquals(ser.getMessage().equals("SUCCESS"), true);
        Assert.assertNotEquals(ser.getData().size(), 0);

        for (Data d :
                ser.getData()) {
            Assert.assertEquals(d.getLogo().trim().startsWith("https://"), true);
            Assert.assertEquals(d.getExchange().equals(""), false);

        }
    }

    @Test(description = "交易对信息")
    public void Test_swap_exchanges_01HighVersionWithNoSig() {

        Map<String,String> params = new HashMap<String,String>();
        params.put("type", "1");
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("System","Android");
        headers.put("Version","4.13.0");
        response = TronlinkApiList.swapExchangesNoSig(params,headers);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(20004, responseContent.getIntValue("code"));
        Assert.assertEquals("Error param.", responseContent.getString("message"));
    }



    @Test(description = "交易对信息")
    public void Test_swap_exchanges_01() {

        Map<String,String> params = new HashMap<String,String>();
        params.put("address",quince_B58);
        params.put("type", "1");
        response = TronlinkApiList.swapExchanges(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        String apStr = TronlinkApiList.parseResponse2String(response);
        log.info(apStr);
        SwapExRsp ser = JSONObject.parseObject(apStr, SwapExRsp.class);
        org.testng.Assert.assertEquals(ser.getCode(), 0);
        Assert.assertEquals(ser.getMessage().equals("SUCCESS"), true);
        Assert.assertNotEquals(ser.getData().size(), 0);

        for (Data d :
                ser.getData()) {
            Assert.assertEquals(d.getLogo().trim().startsWith("https://"), true);
            Assert.assertEquals(d.getExchange().equals(""), false);

        }
    }

}
