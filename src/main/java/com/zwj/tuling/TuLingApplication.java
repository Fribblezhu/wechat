package com.zwj.tuling;

import com.alibaba.fastjson.JSONObject;
import com.zwj.tuling.service.TuLingServiceImpl;
import com.zwj.wx.message.BaseMessage;
import com.zwj.wx.utils.Aes;
import com.zwj.wx.utils.HttpUtils;
import com.zwj.wx.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author: zwj
 * @Date: 12/3/18
 * @Time: 9:01 PM
 * @description:
 */
@Component
public class TuLingApplication {

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
        String answer = HttpUtils.sendPost(object.toJSONString(),API_URL);
        return tuLingService.parserAnswer(answer);
    }

}
