package com.zwj.Base;

import com.zwj.wx.message.BaseMessage;

/**
 * @author: zwj
 * @Date: 12/10/18
 * @Time: 10:35 AM
 * @description:
 */
public interface BaseRobot extends BaseWorker {

    BaseMessage execute(String content);


}
