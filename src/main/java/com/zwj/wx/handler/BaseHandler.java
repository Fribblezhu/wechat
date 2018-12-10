package com.zwj.wx.handler;

import com.zwj.wx.message.BaseMessage;

/**
 * @author: zwj
 * @Date: 12/4/18
 * @Time: 8:07 PM
 * @description:
 */
public interface BaseHandler {

    BaseMessage handler(String content, String userId);

}
