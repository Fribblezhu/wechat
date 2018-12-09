package com.zwj.wx.message;

import java.util.List;

/**
 * @author: zwj
 * @Date: 12/3/18
 * @Time: 8:26 PM
 * @description:
 */
public class NewsMessage extends BaseMessage{

    private int ArticleCount;

    private List<News> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<News> getArticles() {
        return Articles;
    }

    public void setArticles(List<News> articles) {
        Articles = articles;
    }

}
