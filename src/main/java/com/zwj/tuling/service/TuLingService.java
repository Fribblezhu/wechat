package com.zwj.tuling.service;


import com.zwj.wx.message.BaseMessage;

/**
 * @author: zwj
 * @Date: 12/3/18
 * @Time: 8:40 PM
 * @description:
 */
public interface TuLingService {

    BaseMessage parserAnswer(String answer);
}
