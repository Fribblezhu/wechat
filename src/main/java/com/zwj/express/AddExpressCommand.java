package com.zwj.express;

import com.zwj.Base.BaseCommand;
import com.zwj.constant.RedisConst;
import com.zwj.wx.message.BaseMessage;
import com.zwj.wx.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author: zwj
 * @Date: 12/6/18
 * @Time: 2:37 PM
 * @description:
 */
@Component
public class AddExpressCommand implements BaseCommand {

    // command like "#command:addExpress:name:order"
    private final String prefix = "#command:addExpress";

    private RedisTemplate<String, String> redisTemplate;

    public AddExpressCommand(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate  =redisTemplate;
    }


    @Override
    public BaseMessage execute(String commandStr) {
        String[] commands = commandStr.split(":");
        if(commands.length >= 4) {
            String uuid = UUID.randomUUID().toString();
            redisTemplate.opsForHash().put(RedisConst.EXPRESS_USER_KEY+ "_" + commands[2],  uuid, commands[3]);
            return MessageUtils.assembleTextMessage(this.getReturnContent(true, commands[2], commands[3]));
        }
        return MessageUtils.assembleTextMessage(this.getReturnContent(false, null, null));
    }


    @Override
    public boolean isDuty(String context) {
        return  context.startsWith(this.prefix);
    }


    public String getReturnContent(boolean success, String name, String order){
        StringBuilder builder = new StringBuilder();
        if(success) {
            builder.append("订单号添加成功:\r\n");
            builder.append("姓名:").append(name).append("\r\n");
            builder.append("单号:").append(order).append("\r\n");
        }else  {
            builder.append("订单号添加失败!\r\n");
            builder.append("请按照下面指令格式操作：\r\n");
            builder.append("#command:addExpress:姓名:订单号");
        }
        return builder.toString();
    }


}
