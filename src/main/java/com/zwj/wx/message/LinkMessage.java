package com.zwj.wx.message;

/**
 * @author: zwj
 * @Date: 12/3/18
 * @Time: 8:27 PM
 * @description:
 */
public class LinkMessage extends BaseMessage{

    private String Content;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
