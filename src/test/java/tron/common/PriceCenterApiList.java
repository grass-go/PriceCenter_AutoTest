package tron.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import javafx.beans.binding.MapExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.junit.Assert;
import tron.common.utils.Configuration;
import tron.priceCenter.base.priceBase;
import tron.tronlink.base.TronlinkBase;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Map;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Slf4j
public class PriceCenterApiList {

    static HttpClient httpClient;
    static HttpGet httpget;
    static HttpGet httpGet;

    public static String HttpNode = priceBase.priceUrl;
    //public static String HttpNode = "https://c.tronlink.org/";
    public static String TranscanHttpNode = priceBase.tronscanApiUrl;
    static HttpResponse response;
    static Integer connectionTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpConnectionTimeout");
    static Integer soTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpSoTimeout");
    static JSONObject responseContent;
    static Long requestTime = 0L;
    private static BigDecimal FIVE = new BigDecimal("5");
    public static Map<String,String> ExgTokenAddressMap= new HashMap<>();
    public static Map<String,String> exp_exgTokenValueMap = new HashMap<>();

    public static Map<String,String> trxPriceMap= new HashMap<>();


    static {
        PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
        pccm.setDefaultMaxPerRoute(80);
        pccm.setMaxTotal(100);

        httpClient = new DefaultHttpClient(pccm);
    }

    public static HttpResponse createGetConnect(URI uri) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            log.info("url: " +uri.toString());
            httpGet = new HttpGet(uri);
            httpGet.setHeader("Content-type", "application/json; charset=utf-8");
            httpGet.setHeader("Connection", "Close");
            response = httpClient.execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
            httpGet.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse createGetConnectWithHeader(String url, Map<String, String> params,
                                                          JSONObject requestBody,Map<String,String> header) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httpget = new HttpGet(url);
            if(header != null){
                for(String key: header.keySet()){
                    httpget.setHeader(key,header.get(key));
                    log.info("Add key to header: " + key+": "+header.get(key));
                }
            }
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
                log.info("url: "+httpget.toString()+"\nparams: "+requestBody.toString());
            }

            log.info("" + httpget);

            response = httpClient.execute(httpget);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }
    //get TRX USD price from CMC
    public static String getTrxPricefromCMC(String exchageType){
        Map<String,String> header = new HashMap<>();
        header.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        header.put("Content-Type","MediaType.MULTIPART_FORM_DATA");
        header.put("X-CMC_PRO_API_KEY","8cd87197-8386-4bcb-835c-ff7b78d6ba48");
        header.put("Accept","application/json");
        Map<String,String> params = new HashMap<>();
        params.put("symbol","TRX");
        params.put("convert",exchageType);
        String CMCurl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
        response = createGetConnectWithHeader(CMCurl,params,null,header);
        JSONObject responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Object expPrice = JSONPath.eval(responseContent, String.join("", "$..data.TRX.quote."+exchageType+".price[0]"));
        String trxPrice = expPrice.toString();
        return trxPrice;
    }
    public static void SetTrxPriceMap() {
        trxPriceMap.put("USD",getTrxPricefromCMC("USD"));
        trxPriceMap.put("USDT",getTrxPricefromCMC("USDT"));
        trxPriceMap.put("CNY",getTrxPricefromCMC("CNY"));
        trxPriceMap.put("EUR",getTrxPricefromCMC("EUR"));
        trxPriceMap.put("GBP",getTrxPricefromCMC("GBP"));
    }

    public static String exchangeOtherPriceToTrxPrice(String exchangeValue, String exchageRate){
        BigDecimal exchangeValue_bd = new BigDecimal(exchangeValue);
        BigDecimal exchangeRate_bd = new BigDecimal(exchageRate);
        BigDecimal price = exchangeValue_bd.divide(exchangeRate_bd,18, 1);
        return price.toString();
    }

    public static String getUSDTPriceFromCoinbase(String exchageType){
        String coinbaseURL = "https://www.coinbase.com/api/v2/assets/prices/b26327c1-9a34-51d9-b982-9b29e6012648?base="+exchageType;
        //Map<String,String> params = new HashMap<>();
        response = createGetConnectWithHeader(coinbaseURL,null,null,null);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        //log.info(responseContent.toString());
        Object usdtPrice = JSONPath.eval(responseContent, String.join("", "$..data.prices.latest[0]"));
        String usdtPriceStr = usdtPrice.toString();
        return usdtPriceStr;
    }

    /*public static void SetUSDTPriceMap() throws URISyntaxException {
        usdtPriceMap.put("USD",getUSDTPriceFromCoinbase("USD"));
        usdtPriceMap.put("CNY",getUSDTPriceFromCoinbase("CNY"));
        usdtPriceMap.put("EUR",getUSDTPriceFromCoinbase("EUR"));
        usdtPriceMap.put("GBP",getUSDTPriceFromCoinbase("GBP"));
        String trxusd = getTrxUSDPrice();
        BigDecimal trxusd_bd = new BigDecimal(trxusd);
        BigDecimal usdtusd_bd = new BigDecimal(usdtPriceMap.get("USD"));
        BigDecimal usdttrx_bd = usdtusd_bd.divide(trxusd_bd,18,1);
        usdtPriceMap.put("TRX",usdttrx_bd.toString());
    }*/

    public static HttpResponse getprice(Map<String, String> param) {
        try{
            String requestUrl = HttpNode + "/v1/cryptocurrency/getprice";
            URIBuilder builder = new URIBuilder(requestUrl);
            if(param !=null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI requestUri = builder.build();
            response = createGetConnect(requestUri);
            return response;
        } catch (Exception e){
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
    }

    public static HttpResponse searchtypeFromTranScan(Map<String, String> param) {
        try{
            String requestUrl = TranscanHttpNode + "/api/search-type";
            URIBuilder builder = new URIBuilder(requestUrl);
            if(param !=null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI requestUri = builder.build();
            response = createGetConnect(requestUri);
            return response;
        } catch (Exception e){
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
    }

    public static HttpResponse getallprice(){
        try {
            String requestUrl = HttpNode + "/v1/cryptocurrency/getallprice";
            URIBuilder builder = new URIBuilder(requestUrl);
            URI requestUri = builder.build();
            response = createGetConnect(requestUri);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
    }
    //Compare if string format number gap in tolerance.
    public static boolean CompareGapInTolerance(String expectedstr, String actualstr) {
        BigDecimal expected = new BigDecimal(expectedstr);
        BigDecimal actual = new BigDecimal(actualstr);
        BigDecimal tolerance = expected.divide(FIVE, 18, 1);
        BigDecimal absgap = actual.subtract(expected).abs();
        log.info("expected:"+ expectedstr +", actual:" + actualstr + ", GAP" + absgap + ", tolerance:"+ tolerance.toString());

        Boolean InTolerance = (absgap.compareTo(tolerance) == -1);
        return InTolerance;
    }
    public static boolean CompareLastUpdateInTolerance(long caseTime, long apiTime) {
        long timeTolerance = 60000;
        if (Math.abs((caseTime - apiTime)) >timeTolerance){
            return false;
        }else{
            return true;
        }
    }

    public static void prepareTokensAndPrice(){
        ExgTokenAddressMap.put("USDT", "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
        ExgTokenAddressMap.put("BTC", "TN3W4H6rK2ce4vX9YnFQHwKENnHjoxb3m9");
        ExgTokenAddressMap.put("ETH", "THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF");
        ExgTokenAddressMap.put("JST", "TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9");
        ExgTokenAddressMap.put("LTC", "TR3DLthpnDdCGabhVDbD3VMsiJoCXY3bZd");
        ExgTokenAddressMap.put("HT", "TDyvndWuvX5xTBwHPYJi7J3Yq8pq8yh62h");
        ExgTokenAddressMap.put("SUN", "TSSMHYeV2uE9qYH95DqyoCuNCzEL1NvU3S");
        ExgTokenAddressMap.put("BTT", "TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4");
        ExgTokenAddressMap.put("WIN", "TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
        ExgTokenAddressMap.put("USDD", "TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn");
        ExgTokenAddressMap.put("TUSD", "TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4");
        ExgTokenAddressMap.put("USDJ", "TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT");
        ExgTokenAddressMap.put("USDC", "TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8");
        ExgTokenAddressMap.put("NFT", "TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq");



        //get price from tronscan as expected.
        for (Map.Entry<String, String> entry : ExgTokenAddressMap.entrySet()) {
            String symbol = entry.getKey();
            String address = entry.getValue();
            String reqestUrl = "https://apilist.tronscan.org/api/token_trc20?contract=" + address + "&showAll=1";
            HttpResponse transcanRsp = TronlinkApiList.createGetConnect(reqestUrl);
            JSONObject transcanRspContent = TronlinkApiList.parseJsonObResponseContent(transcanRsp);
            log.info(String.join("", "$..trc20_tokens[contract_address='", address, "'].contract_name"));
            Object expPrice = JSONPath.eval(transcanRspContent, String.join("", "$..trc20_tokens[contract_address='", address, "'].market_info.priceInTrx[0]"));
            String trxPrice = expPrice.toString();
            exp_exgTokenValueMap.put(symbol,trxPrice);
            log.info("symbol: "+symbol+", address:"+address+", trxPrice:"+trxPrice);
        }

    }
}
