package com.zwj.tuling;

import com.alibaba.fastjson.JSONObject;
import com.zwj.tuling.service.TuLingServiceImpl;
import com.zwj.wx.message.BaseMessage;
import com.zwj.wx.utils.Aes;
import com.zwj.wx.utils.HttpUtils;
import com.zwj.wx.utils.Md5;
import com.zwj.wx.utils.MessageUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: zwj
 * @Date: 12/3/18
 * @Time: 9:01 PM
 * @description:
 */
@Component
public class TuLingApplication {

    private CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    @Autowired
    private TuLingServiceImpl tuLingService;


    @Value("#{prop.TuLing_API_URL}")
    public String API_URL;

    @Value("#{prop.TuLing_API_KEY}")
    public String API_KEY;

    @Value("#{prop.TuLing_API_SECRET}")
    public String API_SECRET;


    public BaseMessage getTuLingAnswer(String question, String userid){

        String data= "{\"key\":\""+API_KEY+"\",\"info\":\""+question+"\",\"userid\":\""+userid+"\"}";
        String timestamp = String.valueOf(System.currentTimeMillis());

        //生成密钥
        String keyParam = API_SECRET + timestamp +  API_KEY;
        String key = Md5.MD5(keyParam);

        //对数据加密
        Aes aes = new Aes(key);
        data = aes.encrypt(data);

        //封装数据
        JSONObject object = new JSONObject();
        object.put("key",API_KEY);
        object.put("timestamp",timestamp);
        object.put("data",data);
        //获取请求
        HttpPost post = new HttpPost(API_URL);
        StringEntity entity = new StringEntity(object.toJSONString(), ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        try {
            CloseableHttpResponse response = this.httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
                return tuLingService.parserAnswer(EntityUtils.toString(response.getEntity(), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MessageUtils.assembleTextMessage("恩？机器人失踪了？？？");
    }

}
