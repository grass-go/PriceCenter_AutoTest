package tron.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.api.Http;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;
import javafx.beans.binding.MapExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.junit.Assert;
import org.tron.api.GrpcAPI;
import org.tron.api.WalletGrpc;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.core.Wallet;
import org.tron.protos.contract.SmartContractOuterClass;
import tron.common.utils.Configuration;
import tron.priceCenter.base.priceBase;
import tron.tronlink.base.TronlinkBase;

import java.io.*;
import java.math.BigInteger;
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
    static HttpPost httppost;

    //public static String HttpNode = priceBase.priceUrl;
    public static String HttpNode = priceBase.testPriceUrl;
    //public static String HttpNode = "https://c.tronlink.org/";
    public static String TranscanHttpNode = priceBase.tronscanApiUrl;
    static HttpResponse response;
    static Integer connectionTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpConnectionTimeout");
    static Integer soTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpSoTimeout");
    static JSONObject responseContent;
    static Long requestTime = 0L;
    private static BigDecimal FIVE = new BigDecimal("500");
    public static Map<String,String> ExgTokenAddressMap= new HashMap<>();
    public static Map<String,String> exp_exgTokenValueMap = new HashMap<>();
    public static Map<String,String> JTokenAddressMap = new HashMap<>();
    public static Map<String,String> exp_JTokenValueMap = new HashMap<>();
    public static Map<String,String> Token0xSymbolMap = new HashMap<>();
    public static Map<String,String> exp_0xTokenValueMap = new HashMap<>();
    public static Map<String,String> trxPriceMap= new HashMap<>();
    public static Map<String,String> tokenDecimalMap = new HashMap<>();
    public static Map<String,String> newPoolAddressMap = new HashMap<>();

    static {
        PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
        pccm.setDefaultMaxPerRoute(80);
        pccm.setMaxTotal(100);

        httpClient = new DefaultHttpClient(pccm);
        tokenDecimalMap.put("2USD", "18");   //USDD(18), USDT(6)
        tokenDecimalMap.put("3USD","18"); //USDD,TUSD,USDT
        tokenDecimalMap.put("usdc3SUN","18"); //USDD, 3SUN(USDT,TUSD,USDJ)
        tokenDecimalMap.put("USDD","18");
        tokenDecimalMap.put("USDT","6");
        tokenDecimalMap.put("TUSD","18");
        tokenDecimalMap.put("USDC","6");
        tokenDecimalMap.put("3SUN","18");

        newPoolAddressMap.put("2USD", "TXcJ6pCEGKeLEYXrVnLhqpCVuKfV6wgsfC");   //USDD(18), USDT(6)
        newPoolAddressMap.put("3USD","TA1TVZdERDRDGi9QXNdLVfPxbymmi8xFyc"); //USDD,TUSD,USDT
        newPoolAddressMap.put("usdc3SUN","TQ4i5sdj1VGYGFcivyqFW9NXqzpaP6X8BA"); //USDD, 3SUN(USDT,TUSD,USDJ)

        JTokenAddressMap.put("JTRX", "TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP");
        JTokenAddressMap.put("JUSDT", "TXJgMdjVX5dKiQaUi9QobwNxtSQaFqccvd");
        JTokenAddressMap.put("JTUSD", "TSXv71Fy5XdL3Rh2QfBoUu3NAaM4sMif8R");
        JTokenAddressMap.put("JBTC", "TLeEu311Cbw63BcmMHDgDLu7fnk9fqGcqT");
        JTokenAddressMap.put("JETH", "TR7BUFRQeq1w5jAZf1FKx85SHuX6PfMqsV");
        JTokenAddressMap.put("JSUNOLD", "TGBr8uh9jBVHJhhkwSJvQN2ZAKzVkxDmno");
        JTokenAddressMap.put("JJST", "TWQhCXaWz4eHK4Kd1ErSDHjMFPoPc9czts");
        JTokenAddressMap.put("JUSDJ", "TL5x9MtSnDy537FXKx53yAaHRRNdg9TkkA");
        //JTokenAddressMap.put("JWBTT", "TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt");  与tronscan比价格差距太大,已单独写用例。
        //JTokenAddressMap.put("JNFT", "TFpPyDCKvNFgos3g3WVsAqMrdqhB81JXHE"); 差别大，临时取消
        JTokenAddressMap.put("JWIN", "TRg6MnpsFXc82ymUPgf5qbj59ibxiEDWvv");
        JTokenAddressMap.put("JUSDD", "TX7kybeP6UwTBRHLNPYmswFESHfyjm9bAS");

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

    public static HttpResponse createPostConnectWithHeader(String url, Map<String, String> params,
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
            httppost = new HttpPost(url);
//      httppost.setHeader("Content-type", "application/json; charset=utf-8");
//      httppost.setHeader("Connection", "Close");
            if(header != null){
                for(String key: header.keySet()){
                    httppost.setHeader(key,header.get(key));
                    log.info(key+": "+header.get(key));
                }
            }
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            log.info("url: "+httppost.toString()+"\nparams: "+requestBody.toString());
            response = httpClient.execute(httppost);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
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
    public static String getZoomInValue(String origin,String zoomIn){
        BigDecimal rate = new BigDecimal(zoomIn);
        BigDecimal origin_bd = new BigDecimal(origin);
        BigDecimal percentValue_bd = origin_bd.divide(rate,18,1);
        String percentValue = percentValue_bd.toString();
        return percentValue;
    }

    public static String getPricefromCMC(String symbol,String convert){
        Map<String,String> header = new HashMap<>();
        header.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        header.put("Content-Type","MediaType.MULTIPART_FORM_DATA");
        header.put("X-CMC_PRO_API_KEY","8cd87197-8386-4bcb-835c-ff7b78d6ba48");
        header.put("Accept","application/json");
        Map<String,String> params = new HashMap<>();
        params.put("symbol",symbol);
        params.put("convert",convert);
        String CMCurl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
        response = createGetConnectWithHeader(CMCurl,params,null,header);
        JSONObject responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Object expPrice = JSONPath.eval(responseContent, String.join("", "$..data.",symbol,".quote."+convert+".price[0]"));
        String trxPrice = expPrice.toString();
        return trxPrice;
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
            //String requestUrl = HttpNode + "/v1/cryptocurrency/getprice";
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

    public static JSONObject getprice(String symbol, String convert){
        Map<String,String> params = new HashMap<>();
        params.clear();
        params.put("symbol", symbol);
        params.put("convert", convert);
        response = PriceCenterApiList.getprice(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        return responseContent;
    }

    public static void getprice(Map<String, String> tokensMap, Map<String, String> pricemap, String convertSymbol){
        List<String> joined = getJoinedSymbolAndAddress(tokensMap);
        String symbol = joined.get(0);
        String convert = convertSymbol;
        JSONObject content = getprice(symbol,convert);
        for (Map.Entry<String, String> entry : tokensMap.entrySet()) {
            String curSymbol = entry.getKey();
            String curSymbol_upper = curSymbol.toUpperCase();
            Object curPrice_obj = JSONPath.eval(content,"$..data."+curSymbol_upper+".quote."+ convertSymbol +".price[0]");
            log.info("getCentrePriceForTokenMapBySymbol:jsonpath:"+"$..data."+curSymbol_upper+".quote."+ convertSymbol +".price[0]");
            log.info("getCentrePriceForTokenMapBySymbol:curSymbol:"+curSymbol_upper+", curPrice:"+curPrice_obj.toString());
            pricemap.put(curSymbol,curPrice_obj.toString());
        }
        log.info("getCentrePriceForTokenMapBySymbol:"+pricemap.toString());
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

    public static String getallprice() throws URISyntaxException {
        String requestUrl = HttpNode + "v1/cryptocurrency/getallprice";
        URIBuilder builder = new URIBuilder(requestUrl);
        URI requestUri = builder.build();
        HttpResponse curResponse = createGetConnect(requestUri);
        responseContent = TronlinkApiList.parseJsonObResponseContent(curResponse);
        String response_str = responseContent.toString();
        log.info("response_str");
        return response_str;
    }

    public static java.util.List<String> getTRXandUSDbyfTokenAddrFromAllAPI(JSONObject allpriceResponseContent, String jsonpath){
        java.util.List<String> prices= new java.util.ArrayList<String>();
        Object itemsArray = JSONPath.eval(allpriceResponseContent, jsonpath);
        JSONArray jsonArray = (JSONArray)itemsArray;
        String trxPrice = "";
        String usdPrice = "";
        for (int i=0; i<jsonArray.size(); i++ ){
            JSONObject curItem = jsonArray.getJSONObject(i);
            log.info("curItem:"+curItem.toString());
            String price = (String) curItem.get("price");
            log.info("price:"+price);
            if(curItem.get("sShortName").equals("TRX")){
                trxPrice = price;
            }else if ( curItem.get("sShortName").equals("USD")) {
                usdPrice = price;
            }
        }
        prices.add(trxPrice);
        prices.add(usdPrice);
        return prices;
    }

    public static String getallprice(JSONObject curResponseContent, String tokenSymbol, String tokenAddress,String priceUnit) {
        //responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        log.info("getallprice: curSymbol:"+tokenSymbol+", curAddress:"+tokenAddress);
        java.util.List<String> prices = getTRXandUSDbyfTokenAddrFromAllAPI(curResponseContent,"$..data.rows[fTokenAddr='"+tokenAddress+"']");
        log.info("getallprice:prices:"+prices.toString());
        String returnprice = "";
        if (priceUnit.equals("TRX")){
            returnprice = prices.get(0);
        }else if (priceUnit.equals("USD")){
            returnprice = prices.get(1);
        }
        log.info("getallprice:returnprice:"+returnprice);
        return returnprice;
    }

    //Compare if string format number gap in tolerance.
    public static boolean CompareGapInTolerance(String expectedstr, String actualstr) {
        BigDecimal expected = new BigDecimal(expectedstr);
        BigDecimal actual = new BigDecimal(actualstr);
        BigDecimal tolerance = expected.divide(FIVE, 18, 1);
        BigDecimal absgap = actual.subtract(expected).abs();
        log.info("expected:"+ expectedstr +", actual:" + actualstr + ", GAP:" + absgap + ", tolerance:"+ tolerance.toString());

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

    public static String getTronscanPrice(String address){
        String reqestUrl = "https://apilist.tronscan.org/api/token_trc20?contract=" + address + "&showAll=1";
        HttpResponse transcanRsp = TronlinkApiList.createGetConnect(reqestUrl);
        JSONObject transcanRspContent = TronlinkApiList.parseJsonObResponseContent(transcanRsp);
        //log.info(String.join("", "$..trc20_tokens[contract_address='", address, "'].contract_name"));
        Object expPrice = JSONPath.eval(transcanRspContent, String.join("", "$..trc20_tokens[contract_address='", address, "'].market_info.priceInTrx[0]"));
        String trxPrice = expPrice.toString();
        return trxPrice;
    }

    public static void prepareExchangeTokensAndPrice(){


        //get price from tronscan as expected.
        for (Map.Entry<String, String> entry : ExgTokenAddressMap.entrySet()) {
            String symbol = entry.getKey();
            String address = entry.getValue();
            String trxPrice = getTronscanPrice(address);
            exp_exgTokenValueMap.put(symbol,trxPrice);
            log.info("symbol: "+symbol+", address:"+address+", trxPrice:"+trxPrice);
        }
    }

    public static void preparejTokensAndPrice(){
        for (Map.Entry<String, String> entry : JTokenAddressMap.entrySet()) {
            String symbol = entry.getKey();
            String address = entry.getValue();
            String trxPrice = getTronscanPrice(address);
            exp_JTokenValueMap.put(symbol,trxPrice);
            log.info("symbol: "+symbol+", address:"+address+", trxPrice:"+trxPrice);
        }
    }

    public static void prepare0xTokensAndPrice() throws IOException {
        File tokenFile = new File("src/test/resources/TestData/Price/tokens0x.txt");
        if (tokenFile.isFile() && tokenFile.exists()){
            InputStreamReader Reader = new InputStreamReader(new FileInputStream(tokenFile),"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(Reader);
            String lineTxt = null;
            while ((lineTxt=bufferedReader.readLine())!=null){
                log.info("prepare0xTokensAndPrice:"+lineTxt);
                String []lineList = lineTxt.split(",");
                Token0xSymbolMap.put(lineList[1],lineList[0]);
            }
            Reader.close();
        } else {
            log.info("prepare0xTokensAndPrice: Can't find 0xToken file: src/test/resources/TestData/Price/tokens0x.txt");
        }

        for (Map.Entry<String, String> entry : Token0xSymbolMap.entrySet()) {
            String symbol = entry.getKey();
            String address = entry.getValue();
            String trxPrice = getTronscanPrice(address);
            exp_JTokenValueMap.put(symbol,trxPrice);
            log.info("symbol: "+symbol+", address:"+address+", trxPrice:"+trxPrice);
        }
    }

    public static List<String> getJoinedSymbolAndAddress(Map<String, String> tokensMap){
        String joinedSymbols="";
        String joinedAddresses="";
        for (Map.Entry<String, String> entry : tokensMap.entrySet()) {
            joinedSymbols = joinedSymbols+entry.getKey()+",";
            joinedAddresses = joinedAddresses+entry.getValue()+",";
        }
        joinedSymbols = joinedSymbols.substring(0,joinedSymbols.length()-1);
        joinedAddresses = joinedAddresses.substring(0,joinedAddresses.length()-1);
        List<String> returnValue = new java.util.ArrayList<String>();
        returnValue.add(joinedSymbols);
        returnValue.add(joinedAddresses);
        return returnValue;
    }

    public static HttpResponse triggerConstantContract(String body_str){
        Map<String, String> header = new HashMap<>();
        String gridURL = "https://api.trongrid.io/wallet/triggerconstantcontract";
        header.clear();
        header.put("Content-Type","application/json");
        header.put("Accept","application/json");
        JSONObject body = JSON.parseObject(body_str);
        response = PriceCenterApiList.createPostConnectWithHeader(gridURL,null,body,header);
        return response;
    }

    public static String getTotalSupplyOfOneCoin(String symbol, String coinAddress){
        String body_str = "{\n" +
                "\n" +
                "    \"owner_address\": \"TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t\",\n" +
                "    \"contract_address\": \"" + coinAddress +"\",\n" +
                "    \"function_selector\": \"totalSupply()\",\n" +
                "    \"visible\": true\n" +
                "}";
        response = PriceCenterApiList.triggerConstantContract(body_str);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        log.info(responseContent.toString());
        Object result = JSONPath.eval(responseContent,"$..constant_result[0]");
        String result_str = result.toString();
        log.info("getTotalSupplyOfOneCoin:result:"+result_str);
        String hexValue =  result_str.replaceAll("^(0+)", "");
        BigDecimal totalSupply = new BigDecimal(new BigInteger(hexValue, 16));
        log.info("getTotalSupplyOfOneCoin:hexValue:" + hexValue +", totalSupply:"+totalSupply);
        int decimal = Integer.parseInt(tokenDecimalMap.get(symbol));
        BigDecimal totalSupply_real = totalSupply.divide(new BigDecimal(10).pow(decimal),18,1);
        log.info("getTotalSupplyOfOneCoin:decimal:"+decimal+", totalSupply_real:"+totalSupply_real);
        String USD_2Price=totalSupply_real.toString();
        return USD_2Price;
    }

    //paramter: 为balances(uint256)的参数。
    //symbol：获取token的精度使用。
    public static String getAmountOfSubCoin(String symbol, String poolAddress,String parameter) {
        String body_str = "{\n" +
                "\n" +
                "    \"owner_address\": \"TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t\",\n" +
                "    \"contract_address\": \"" + poolAddress +"\",\n" +
                "    \"function_selector\": \"balances(uint256)\",\n" +
                "    \"parameter\": \"" + parameter + "\",\n" +
                "    \"visible\": true\n" +
                "}";
        response = PriceCenterApiList.triggerConstantContract(body_str);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        log.info(responseContent.toString());
        Object result = JSONPath.eval(responseContent,"$..constant_result[0]");
        String result_str = result.toString();
        log.info("getAmountOfSubCoin:result:"+result_str);
        String hexValue =  result_str.replaceAll("^(0+)", "");
        BigDecimal amount = new BigDecimal(new BigInteger(hexValue, 16));
        log.info("getAmountOfSubCoin:hexValue:" + hexValue +", totalSupply:"+amount);
        int decimal = Integer.parseInt(tokenDecimalMap.get(symbol));
        BigDecimal amount_real = amount.divide(new BigDecimal(10).pow(decimal),18,1);
        log.info("getAmountOfSubCoin:decimal:"+decimal+", totalSupply_real:"+amount_real);
        return amount_real.toString();
    }

}
