package com.zwj.wx.utils;

import com.thoughtworks.xstream.XStream;
import com.zwj.wx.message.BaseMessage;
import com.zwj.wx.message.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zwj
 * @Date: 12/3/18
 * @Time: 7:52 PM
 * @description:
 */
public class MessageUtils {

    private static final XStream xStream = new XStream();

    /**
     * 将传过来的xml信息转成map集合
     * @param content
     * @return
     */
    public static Map<String,String> toMap(String content) {

        Map<String, String> map = new HashMap<>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(content);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        if(doc != null) {
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
        }

        return map;
    }

    /**
     * 接受一个对象类型,返回xml字符串
     * @param message
     * @return
     */
    public static String toXml(BaseMessage message){
        return xStream.toXML(message);
    }

    public static TextMessage assembleTextMessage(String content) {
        TextMessage message = new TextMessage();
        message.setContent(content);
        message.setCreateTime(String.valueOf(System.currentTimeMillis()));
        return message;
    }

}
