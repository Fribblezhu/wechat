package com.zwj.manager;

import com.zwj.Base.BaseCommand;
import com.zwj.express.AddExpressCommand;
import com.zwj.wx.message.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: zwj
 * @Date: 12/5/18
 * @Time: 10:56 PM
 * @description:
 */
@Component
public class CommandManger extends AbstractManager {

    private String prefix = "#command";

    @Autowired
    public CommandManger(AddExpressCommand addCommand) {
        this.workers.add(addCommand);
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
