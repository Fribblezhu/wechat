package com.zwj.express;

import com.zwj.Base.BaseCommand;
import com.zwj.constant.RedisConst;
import com.zwj.kuaidi100.KuaiDi100Application;
import com.zwj.wx.message.BaseMessage;
import com.zwj.wx.utils.MessageUtils;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * @author: zwj
 * @Date: 12/6/18
 * @Time: 2:38 PM
 * @description:
 */
public class CheckExpressComand implements BaseCommand {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private KuaiDi100Application kuaiDi100Application = KuaiDi100Application.getInstance();

    @Override
    public BaseMessage execute(String name) {
        String order = getOrder(name);
        if(null == order)  {
            MessageUtils.assembleTextMessage("未找到 " + name +" 的快递单号!");
        }else {
            kuaiDi100Application.getExpressInfo(order);
        }
        return MessageUtils.assembleTextMessage(getReturnContent(false, "系统错误，请联系管理员！"));
    }

    @Override
    public boolean isDuty(String name) {
        return redisTemplate.opsForHash().hasKey(RedisConst.EXPRESS_USER_KEY, name);
    }

    public String getReturnContent(boolean type, String name){
            return name;
    }


    private String getOrder(String key) {
        Map<Object,Object> userMap =  redisTemplate.opsForHash().entries(RedisConst.EXPRESS_USER_KEY);
        String uuid = null;
        for(Object o: userMap.keySet()){
            if(key.equals(String.valueOf(o)))
                uuid = String.valueOf(userMap.get(o));
        }
        if(null != uuid) {
            return redisTemplate.opsForValue().get(uuid);
        }
        return  null;
    }

}
