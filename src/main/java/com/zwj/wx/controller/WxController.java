package com.zwj.wx.controller;

import com.zwj.wx.handler.RequestHandler;
import com.zwj.wx.message.BaseMessage;
import com.zwj.wx.utils.MessageUtils;
import com.zwj.wx.utils.WxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @author: zwj
 * @Date: 12/3/18
 * @Time: 7:45 PM
 * @description:
 */
@Controller
public class WxController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RequestHandler requestHeader;

    @RequestMapping(value = "/wxChat.do", method = RequestMethod.GET)
    @ResponseBody
    public String checkSingature(@RequestParam(name="timestamp") String timestamp,
                               @RequestHeader(name = "signature") String signature,
                               @RequestParam(name="nonce") String nonce,
                               @RequestParam(name = "echostr") String echostr) {

        logger.debug("开始授权签名:");
        if (WxUtils.sha1Hex(timestamp, nonce).equals(signature)) {
            logger.debug("授权签名成功!");
            return echostr;
        }
        logger.debug("授权失败!");
        logger.debug("授权签名结束！");
        return "error";
    }

    @RequestMapping(value = "/wxChat.do", method = RequestMethod.POST)
    public void messageReceiver(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = MessageUtils.toMap(request);
        if (map.size() > 0) {
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                String xmlString = null;
                if ("text".equals(msgType)) {
                    BaseMessage send = requestHeader.handler(content, fromUserName);
                    send.setCreateTime(String.valueOf(new Date().getTime()));
                    send.setFromUserName(toUserName);
                    send.setToUserName(fromUserName);
                    send.setMsgType("text");
                    xmlString = MessageUtils.toXml(send);
                }
                out.write(xmlString);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(null != out)
                    out.close();
            }
        }
    }
    @RequestMapping(value="/test.do", method = RequestMethod.POST)
    @ResponseBody
    public BaseMessage test(@RequestBody String context) {
        BaseMessage message = requestHeader.handler(context, "fribble");
        return message;
    }
}

