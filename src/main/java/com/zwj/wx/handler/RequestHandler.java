package com.zwj.wx.handler;

import com.zwj.Base.BaseManager;
import com.zwj.manager.AbstractManager;
import com.zwj.manager.CommandManger;
import com.zwj.manager.RobotManager;
import com.zwj.tuling.TuLingApplication;
import com.zwj.wx.message.BaseMessage;
import com.zwj.wx.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zwj
 * @Date: 12/4/18
 * @Time: 12:32 AM
 * @description:
 */
@Component
public class RequestHandler implements BaseHandler{

    private List<AbstractManager>  managers = new ArrayList<>();

    @Autowired
    private TuLingApplication tuling;

    @Autowired
    public RequestHandler(CommandManger manger, RobotManager robotManager) {
        this.managers.add(manger);
        this.managers.add(robotManager);
    }


    @Override
    public BaseMessage handler(String content, String userId) {
        for(AbstractManager manager: this.managers) {
            if(manager.isDuty(content)) {
                 BaseMessage response=  manager.dispatch(content);
                 if(response == null)
                     return MessageUtils.assembleTextMessage("很抱歉，系统无法回复你的消息!");
                 return response;
            }
        }
        return tuling.getTuLingAnswer(content, userId);
    }

}
