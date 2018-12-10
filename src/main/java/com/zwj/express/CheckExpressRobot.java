package com.zwj.express;

import com.zwj.Base.BaseCommand;
import com.zwj.Base.BaseRobot;
import com.zwj.constant.RedisConst;
import com.zwj.kuaidi100.KuaiDi100Application;
import com.zwj.wx.message.BaseMessage;
import com.zwj.wx.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zwj
 * @Date: 12/6/18
 * @Time: 2:38 PM
 * @description:
 */
@Component
public class CheckExpressRobot implements BaseRobot {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private KuaiDi100Application kuaiDi100Application = KuaiDi100Application.getInstance();

    @Override
    public BaseMessage execute(String name) {
        List<Object> orders = getOrder(name);
        if(orders.size() == 0)  {
            return MessageUtils.assembleTextMessage("啊哦！"+name +"的快递单号好像消失了!");
        }else {
            StringBuilder allOrdersInfo = new StringBuilder();
            allOrdersInfo.append("你共有").append(orders.size()).append("个订单!\r\n");
            for(Object o: orders) {
                allOrdersInfo.append("单号(").append(String.valueOf(o)).append("):");
                String info = kuaiDi100Application.getExpressInfo(String.valueOf(o));
                allOrdersInfo.append(info).append("\r\n");
                allOrdersInfo.append("#################\r\n");
            }
            return MessageUtils.assembleTextMessage(allOrdersInfo.toString());
        }
    }

    @Override
    public boolean isDuty(String name) {
        return redisTemplate.opsForHash().values(RedisConst.EXPRESS_USER_KEY + "_" + name).size() > 0;
    }

    private List<Object> getOrder(String key) {
        return redisTemplate.opsForHash().values(RedisConst.EXPRESS_USER_KEY + "_" + key);
    }

}
