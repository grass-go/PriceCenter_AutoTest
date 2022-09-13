package tron.tronlink.v2.model;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.util.Map;

public class SigRsp{
    public SigRsp(HttpGet httpGet,HttpPost httpPost, Map<String,String> params){
        this.httpGet = httpGet;
        this.params= params;
        this.httpPost = httpPost;
    }
    public  HttpGet httpGet;
    public HttpPost httpPost;
    public  Map<String,String> params;
}