package tron.tronlink.v2;

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

public class GetSign {
    //ANDROID_TEST("AndroidTest","SFSUIOJBFMLKSJIF", "SKDOE543KLMFSLKMJTIO4JTSSDFDSMKM65765",
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
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


    private String getSignature(String channel, String chain, String lang,
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
        return hmacSHA1("SKDOE543KLMFSLKMJTIO4JTSSDFDSMKM65765", String.format("%s%s%s?%s", method, deviceId, url, makeQueryString(arguments, "utf-8")));

    }
    private String makeQueryString(Map<String, String> args, String charset) throws UnsupportedEncodingException
    {
        String url = "";
        for (Map.Entry<String, String> entry : args.entrySet())
            url += entry.getKey() + "=" + (null == charset ? entry.getValue() : URLEncoder.encode(entry.getValue(), charset)) + "&";
        return url.substring(0, url.length()-1);

    }
    public static void main(String[] args) throws Exception{
        GetSign getSign1 = new GetSign();
        System.out.println(URLEncoder.encode(getSign1.getSignature(
                 "official", "MainChain","1", "41F985738AE54FD87ED6CD07065905EBEA355E66CD",
                "12345", "SFSUIOJBFMLKSJIF", "AndroidTest", "1:1:1:1", "1609302220000",
                "v1.0.0", "/api/wallet/v2/addAsset", "POST")));
        /*System.out.println(URLEncoder.encode(getSign1.getSignature(
                "official", "MainChain","1", "411D6D2E931B2088BB149B98BD35929DE0D958FCF2",
                "12345", "SFSUIOJBFMLKSJIF", "AndroidTest", "1:1:1:1", "1609302220000",
                "v1.0.0", "/api/wallet/v2/addAsset", "POST")));*/
    }

}
