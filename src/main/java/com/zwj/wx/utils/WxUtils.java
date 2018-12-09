package com.zwj.wx.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: zwj
 * @Date: 12/3/18
 * @Time: 8:06 PM
 * @description:
 */
public class WxUtils {

    public final static String TOKEN = "fribblezwj";


    public static String sha1Hex(String timestamp, String nonce) {
        List<String> list = new ArrayList<>();
        list.add(timestamp);
        list.add(nonce);
        list.add(TOKEN);

        Collections.sort(list);
        StringBuffer buffer = new StringBuffer();
        for(String string: list){
            buffer.append(string);
        }

        return  DigestUtils.sha1Hex(buffer.toString());
    }

}
