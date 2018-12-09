package com.zwj.manager;

import com.zwj.Base.BaseCommand;
import com.zwj.wx.message.BaseMessage;
import java.util.Arrays;

/**
 * @author: zwj
 * @Date: 12/5/18
 * @Time: 10:56 PM
 * @description:
 */




public class CommandManger extends AbstractManager {

    private String prefix = "#command";



    public CommandManger(BaseCommand... commands) {
        this.workers = Arrays.asList(commands);
    }



    @Override
    public boolean isDuty(String context) {
        return context.startsWith(this.prefix);
    }

    @Override
    public BaseMessage dispatch(String context) {
        BaseCommand command = (BaseCommand) this.getWork(context);
        if(null != command)
            return command.execute(context);
        return  null;
    }


}
