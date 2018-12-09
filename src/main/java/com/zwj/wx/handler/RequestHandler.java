package com.zwj.wx.handler;

import com.zwj.Base.BaseManager;
import com.zwj.manager.AbstractManager;
import com.zwj.wx.message.BaseMessage;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author: zwj
 * @Date: 12/4/18
 * @Time: 12:32 AM
 * @description:
 */
@Component
public class RequestHandler implements BaseHandler{

    private List<AbstractManager>  managers;

    public RequestHandler(AbstractManager... managers) {
        this.managers = Arrays.asList(managers);
    }


    @Override
    public BaseMessage handler(String content) {
        for(AbstractManager manager: this.managers) {
            if(manager.isDuty(content)) {
                 return manager.dispatch(content);
            }
        }
        return null;
    }

}
