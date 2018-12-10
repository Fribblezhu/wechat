package com.zwj.wx.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * @author: zwj
 * @Date: 12/10/18
 * @Time: 6:47 PM
 * @description:
 */
public class WxUtilsTest {

    @Test
    public void sha1Hex() {
        String signature = "7a0edd055dd44baf79d945febefb5c0bec7344c0";
        String echostr = "9143038137434329231";
        String timestamp = "1544437774";
        String nonce = "550196855";
        System.out.println(WxUtils.sha1Hex(timestamp, nonce));
        Assert.assertEquals(signature,WxUtils.sha1Hex(timestamp, nonce));
    }

}
