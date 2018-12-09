package com.zwj.system;

import com.zwj.Base.BaseCommand;
import com.zwj.wx.message.BaseMessage;

/**
 * @author: zwj
 * @Date: 12/5/18
 * @Time: 10:29 PM
 * @description:
 */
public class ExpressCommandExecuter implements BaseCommand {

    @Override
    public BaseMessage execute(String commandStr) {
        return null;
    }


    @Override
    public boolean isDuty(String context) {
        return false;
    }
}
