package com.zwj.wx.controller;

import com.zwj.wx.handler.RequestHandler;
import com.zwj.wx.message.BaseMessage;
import com.zwj.wx.utils.MessageUtils;
import com.zwj.wx.utils.WxUtils;
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

    @Autowired
    private RequestHandler requestHeader;

    @RequestMapping(value = "/wxChat.do", method = RequestMethod.GET)
    @ResponseBody
    public void checkSingature(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String timestamp = request.getParameter("timestamp");
            String signature = request.getParameter("signature");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            if (WxUtils.sha1Hex(timestamp, nonce).equals(signature)) {
                out.write(echostr);
                out.flush();
            }
        } catch (Exception e) {
            out.write("error");
            out.flush();
        }finally {
            if(null != out)
                out.close();
        }
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

