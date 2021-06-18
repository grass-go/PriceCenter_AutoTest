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

    @Test(enabled = true)
    public void getDelegatedResource01(){

    }

}
