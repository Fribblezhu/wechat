package com.zwj.kuaidi100;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author: zwj
 * @Date: 12/7/18
 * @Time: 4:04 PM
 * @description:
 */
public class KuaiDi100Application {

    private CloseableHttpClient client;

    public static KuaiDi100Application instance;

    public static KuaiDi100Application  getInstance(){
        if(instance == null)
            instance = new KuaiDi100Application();
        return instance;
    }



    private KuaiDi100Application(){
        this.init();
    }

    private void  init() {
        this.client = HttpClientBuilder.create().build();
        this.login();
        this.queryCookies();
    }


    private void login() {
        HttpGet get = new HttpGet("http://www.kuaidi100.com/");
        try {
            this.client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void queryCookies() {
        HttpGet get = new HttpGet("http://cache.kuaidi100.com/querycookie.jsp?jsoncallback=jQuery171045185351579478406_1544164134473&_=1544164402790");
        try {
            CloseableHttpResponse response = this.client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String autoGetExpressCode(String order){
        HttpGet get = new HttpGet("http://www.kuaidi100.com/autonumber/autoComNum?resultv2=1&text="+ order);
        CloseableHttpResponse response = null;
        try {
            response = this.client.execute(get);
            if(response.getStatusLine().getStatusCode() == 200) {
                return  EntityUtils.toString(response.getEntity(), "UTF-8");
            }else{
                this.queryCookies();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // ignore
                }
                response = this.client.execute(get);
                if(response.getStatusLine().getStatusCode() == 200){
                    return  EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getExpressInfo(String order) {
        String expressCodeString = null;
        try {
            expressCodeString = this.autoGetExpressCode(order);
            if(!StringUtils.isEmpty(expressCodeString)){
                JSONObject jsonObject = JSON.parseObject(expressCodeString);
                List<ExpressCodeEntity> expressCodeEntities = jsonObject.getJSONArray("auto").toJavaList(ExpressCodeEntity.class);
                for(ExpressCodeEntity entity: expressCodeEntities) {
                    StringBuilder urlBuilder = new StringBuilder();
                    urlBuilder.append("http://www.kuaidi100.com/query");
                    urlBuilder.append("?type=").append(entity.getComCode());
                    urlBuilder.append("&postid=").append(order);
                    urlBuilder.append("&temp=").append(Math.random());
                    HttpGet get = new HttpGet(urlBuilder.toString());
                    CloseableHttpResponse response = this.client.execute(get);
                    if(response.getStatusLine().getStatusCode() == 200 ) {
                        String resultStr =   EntityUtils.toString(response.getEntity());
                        if(this.isSuccess(resultStr)) {
                            JSONObject result = JSON.parseObject(resultStr);
                            List<ExpressInfoEntity> infos = result.getJSONArray("data").toJavaList(ExpressInfoEntity.class);
                            String info = "时间：" +infos.get(0).getTime() + "\r\n位置：" + infos.get(0).getContext();
                            return info;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isSuccess(String result) {
        JSONObject jsonObject = JSON.parseObject(result);
        return "ok".equals(jsonObject.getString("message"));
    }
}
