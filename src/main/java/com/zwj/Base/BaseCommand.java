package com.zwj.Base;

import com.zwj.wx.message.BaseMessage;

/**
 * @author: zwj
 * @Date: 12/5/18
 * @Time: 10:24 PM
 * @description:
 */
public interface BaseCommand extends  BaseWorker{

    BaseMessage execute(String commandStr);

}
