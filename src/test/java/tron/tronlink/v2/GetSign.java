package tron.tronlink.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

public class GetSign {
    //ANDROID_TEST("AndroidTest","SFSUIOJBFMLKSJIF", "SKDOE543KLMFSLKMJTIO4JTSSDFDSMKM65765",
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private DefaultHttpClient httpClient;

    /**
     * Signature algorithm using HMAC-SHA1
     * @param key
     * @param text
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String hmacSHA1(String key, String text) throws Exception
    {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA1"));
        return encode(mac.doFinal(text.getBytes()));
    }

    /**
     * 编码
     * @param bstr
     * @return String
     */
    private static String encode(byte[] bstr) throws Exception{
        String s = System.getProperty("line.separator");
        //return Base64.getEncoder().encodeToString(bstr).replaceAll(s, "");    // JDK 1.8 可使用
//        return Base64.encodeBase64String(bstr).replaceAll(s, "");             // JDK 1.7及以下使用第三方Jar包 commons-codec
//        return URLEncoder.encode(Base64.getEncoder().encodeToString(bstr).replaceAll(s, ""),"utf-8");
        return Base64.getEncoder().encodeToString(bstr).replaceAll(s, "");
    }


    private static String getSignature(String channel, String chain, String lang,
                                String address, String nonce, String secretId, String system, String deviceId,
                                String ts, String version, String url, String method) throws Exception {
        SortedMap<String, String> arguments = new TreeMap<String, String>();
        arguments.put("System", system);
        arguments.put("Version", version);
        arguments.put("Lang", lang);
        arguments.put("channel", channel);
        arguments.put("chain", chain);
        arguments.put("ts", ts);
        arguments.put("nonce", nonce);
        arguments.put("secretId", secretId);
        arguments.put("address", address);
        System.out.println(String.format("%s%s%s?%s", method, deviceId, url, makeQueryString(arguments, "utf-8")));

//        return hmacSHA1("RERTNJNVJKNKJGNDKJGJGF33G2G246H4H54H4", String.format("%s%s%s?%s", method, deviceId, url, makeQueryString(arguments, "utf-8")));
        String secretKey = "";

        if (system.equals("AndroidTest")){
            secretKey = "SKDOE543KLMFSLKMJTIO4JTSSDFDSMKM65765";
        }else if (system.equals("chrome-extension-test")){
            secretKey = "S8NFNSFJDFJKNFKASNFSJNFKJSN2344SFN2K2";
        }else if (system.equals("IosTest")){
            secretKey = "RERTNJNVJKNKJGNDKJGJGF33G2G246H4H54H4";
        }

        return hmacSHA1(secretKey, String.format("%s%s%s?%s", method, deviceId, url, makeQueryString(arguments, "utf-8")));

    }
    private static String makeQueryString(Map<String, String> args, String charset) throws UnsupportedEncodingException
    {
        String url = "";
        for (Map.Entry<String, String> entry : args.entrySet())
            url += entry.getKey() + "=" + (null == charset ? entry.getValue() : URLEncoder.encode(entry.getValue(), charset)) + "&";
        return url.substring(0, url.length()-1);

    }
    public static void main(String[] args) throws Exception{
        GetSign getSign1 = new GetSign();
        System.out.println(URLEncoder.encode(getSign1.getSignature(
                 "official", "MainChain","1", "TAYzcfLovWdV83g25Apfd7BA67J44D5z5M",
                "12345", "SFSUIOJBFMLKSJIF", "AndroidTest", "1:1:1:1", "1609302220000",
                "v1.0.0", "/api/wallet/v2/assetList", "POST")));
        /*System.out.println(URLEncoder.encode(getSign1.getSignature(
                "official", "MainChain","1", "411D6D2E931B2088BB149B98BD35929DE0D958FCF2",
                "12345", "SFSUIOJBFMLKSJIF", "AndroidTest", "1:1:1:1", "1609302220000",
                "v1.0.0", "/api/wallet/v2/addAsset", "POST")));*/
    }


    public static Map<String, String> getV2Header(){
        Map<String, String> httpget = new HashMap<>();
        httpget.put("Lang","1");
        httpget.put("Version","v1.0.0");
        httpget.put("DeviceID","1:1:1:1");
        httpget.put("chain","MainChain");
        httpget.put("channel","official");
        httpget.put("ts", "1609302220000");
        httpget.put("packageName","com.tronlinkpro.wallet");
        httpget.put("System","AndroidTest");
        httpget.put("Content-type", "application/json; charset=utf-8");
        httpget.put("Connection", "Keep-Alive");
        return httpget;
    }

    @Test
    public void GetResquest() throws Exception {

        String url = "https://list.tronlink.org/api/wallet/nft/getCollectionList?tokenAddress=TCzUYnFSwtH2bJkynGB46tWxWjdTQqL1SG&address=TAVNk5hkaPNJcTf6TvJVgBWEaRhuiHE5Ab&signature=1A8%2FhWTN%2F8g7qKv7vqUdIYKVjJU%3D&pageIndex=0&secretId=SFSUIOJBFMLKSJIF&pageSize=10&nonce=12345";
        url = "https://list.tronlink.org/api/wallet/nft/getCollectionInfo?tokenAddress=TPvGT3tWUNakTg23ARKMx46MGLT386nYWD&address=4105B49C6271FC5B2B8A8E1E980F6A12D9B66E2914&signature=1A8%2FhWTN%2F8g7qKv7vqUdIYKVjJU%3D&assetId=1&secretId=SFSUIOJBFMLKSJIF&nonce=12345";


//        url = "https://testpre.tronlink.org/api/dapp/v2/head";
        String httpHost = url.substring(0,url.indexOf("/api"));
        String path = url.substring(url.indexOf("/api"),url.indexOf("?"));
        Map<String, String> parameter = new HashMap();
        String[] parameterUrl = url.substring(url.indexOf("?") +1).split("&");
        for (String prmU : parameterUrl){
            String key = prmU.substring(0,prmU.indexOf("="));
            String value = prmU.substring(prmU.indexOf("=") + 1);
            if (!key.equals("signature")) {
                parameter.put(key,value);
            }
        }

        Map<String, String> head = getV2Header();
//        head.put("System","chrome-extension-test");
//        parameter.put("secretId","8JKSO2PM4M2K45EL");

        String signature = URLEncoder.encode(getSignature(
            head.get("channel"),
            head.get("chain"),
            head.get("Lang"),
            parameter.get("address"),
            parameter.get("nonce"),
            parameter.get("secretId"),
            head.get("System"),
            head.get("DeviceID"),
            head.get("ts"),
            head.get("Version"),
            path,
            "GET"
        ));
        parameter.put("signature",signature);

        HttpResponse httprespone = TronlinkApiList.createGetConnectWithHeader(httpHost + path, parameter,null,head);
        JSONObject result = TronlinkApiList.parseJsonObResponseContent(httprespone);

        String pretty = JSON.toJSONString(result, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteDateUseDateFormat);
        System.out.println(pretty);
    }

    @Test
    public void PostResquest() throws Exception {

        String url = "https://testpre.tronlink.org/api/wallet/v2/assetList?nonce=12345&secretId=SFSUIOJBFMLKSJIF&signature=JHQAlW9COUvY7OAFt9Aj0n5qWyg%3D&address=TAYzcfLovWdV83g25Apfd7BA67J44D5z5M";
        url = "https://testpre.tronlink.org/api/wallet/v2/allAssetList?address=410663cda2477d40110d2f4ecfa9b076dd7f9a5570&nonce=7693131370817136548&secretId=A4ADE880F46CA8D4&signature=9iYqjcXFh3fPdiAfS%2FtEn8oW3oM%3D";
        url = "https://list.tronlink.org/api/wallet/nft/getCollectionInfos?address=4105B49C6271FC5B2B8A8E1E980F6A12D9B66E2914&signature=1A8%2FhWTN%2F8g7qKv7vqUdIYKVjJU%3D&secretId=SFSUIOJBFMLKSJIF&nonce=12345";

        String httpHost = url.substring(0,url.indexOf("/api"));
        String path = url.substring(url.indexOf("/api"),url.indexOf("?"));
        Map<String, String> parameter = new HashMap();
        String[] parameterUrl = url.substring(url.indexOf("?") +1).split("&");
        for (String prmU : parameterUrl){
            String key = prmU.substring(0,prmU.indexOf("="));
            String value = prmU.substring(prmU.indexOf("=") + 1);
            if (!key.equals("signature")) {
                parameter.put(key,value);
            }
        }

        Map<String, String> head = getV2Header();
//        head.put("System","chrome-extension-test");
//        parameter.put("secretId","8JKSO2PM4M2K45EL");

        String signature = URLEncoder.encode(getSignature(
            head.get("channel"),
            head.get("chain"),
            head.get("Lang"),
            parameter.get("address"),
            parameter.get("nonce"),
            parameter.get("secretId"),
            head.get("System"),
            head.get("DeviceID"),
            head.get("ts"),
            head.get("Version"),
            path,
            "POST"
        ));
        parameter.put("signature",signature);

        JSONObject requestBody = new JSONObject();

        requestBody.put("address", "TAYzcfLovWdV83g25Apfd7BA67J44D5z5M");

        List<String> trc10tokenList = new ArrayList<>();
        trc10tokenList.add("1");
        trc10tokenList.add("133");
        trc10tokenList.add("134");
        requestBody.put("assetIdList",trc10tokenList);
        requestBody.put("tokenAddress", "TPvGT3tWUNakTg23ARKMx46MGLT386nYWD");

        HttpResponse httprespone = TronlinkApiList.createPostConnectWithHeader(httpHost + path, parameter,requestBody,head);
        JSONObject result = TronlinkApiList.parseJsonObResponseContent(httprespone);

        String pretty = JSON.toJSONString(result, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteDateUseDateFormat);
        System.out.println(pretty);
    }
}
