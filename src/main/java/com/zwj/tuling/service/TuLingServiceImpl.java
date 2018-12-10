package com.zwj.tuling.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwj.constant.TuLingConst;
import com.zwj.tuling.model.FoodModel;
import com.zwj.tuling.model.NewsModel;
import com.zwj.wx.message.BaseMessage;
import com.zwj.wx.message.News;
import com.zwj.wx.message.NewsMessage;
import com.zwj.wx.message.TextMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: zwj
 * @Date: 12/3/18
 * @Time: 8:36 PM
 * @description:
 */
@Service
public class TuLingServiceImpl implements TuLingService{


    @Override
    public BaseMessage parserAnswer(String answer) {
        JSONObject object = JSONObject.parseObject(answer);
        int code = object.getIntValue("code");
        BaseMessage message = null;
        switch (code){
            case TuLingConst.RESPONSE_TYPE_TEXT:
                message = text(answer);
                break;
            case TuLingConst.RESPONSE_TYPE_LINK:
                message = link(answer);
                break;
            case TuLingConst.RESPONSE_TYPE_NEWS:
                message = news(answer);
                break;
            case TuLingConst.RESPONSE_TYPE_FOOD:
                message = food(answer);
                break;
            default:
                message = error(answer);
                break;
        }

        return message;

    }

    private BaseMessage loadToMessage(List<News> newsList){
        NewsMessage nMessage = new NewsMessage();
        nMessage.setArticleCount(newsList.size());
        nMessage.setArticles(newsList);
        nMessage.setCreateTime(String.valueOf(new Date().getTime()));
        nMessage.setMsgType("news");
        return nMessage;
    }

    private BaseMessage error(String answer) {
        TextMessage message = new TextMessage();
        message.setCreateTime(String.valueOf(new Date().getTime()));
        message.setContent("啊哦，好像出错了！");
        message.setMsgType("text");
        return message;
    }

    private BaseMessage food(String answer) {
        JSONObject object = JSON.parseObject(answer);
        List<FoodModel> tuFoodList = new ArrayList<>();
        tuFoodList = object.getObject("list",tuFoodList.getClass());
        List<News> newsList = new ArrayList<>();
        for(FoodModel f: tuFoodList ){
            News n = new News();
            n.setTitle(f.getName());
            n.setPicUrl(f.getInfo());
            n.setDescription(f.getInfo());
            n.setUrl(f.getDetailurl());
            newsList.add(n);
        }
        return loadToMessage(newsList);
    }

    private BaseMessage news(String answer) {
        JSONObject object = JSON.parseObject(answer);
        List<NewsModel> tuNewsList = new ArrayList<>();
        tuNewsList = object.getObject("list",tuNewsList.getClass());
        List<News> newsList = new ArrayList<>();
        for(NewsModel t :tuNewsList){
            News n = new News();
            n.setTitle(t.getArticle());
            n.setUrl(t.getDetaiurl());
            n.setDescription(t.getSource());
            n.setPicUrl(" ");
            newsList.add(n);
        }
        return loadToMessage(newsList);
    }

    private BaseMessage link(String answer) {
        JSONObject object = JSONObject.parseObject(answer);
        String content = object.getString("text");
        String url = object.getString("url");
        NewsMessage message = new NewsMessage();
        message.setMsgType("news");
        message.setCreateTime(String.valueOf(new Date().getTime()));
        message.setArticleCount(1);
        News news = new News();
        news.setTitle(content);
        news.setUrl(url);
        news.setDescription(" ");
        news.setPicUrl(" ");
        List<News> newses = new ArrayList<>();
        newses.add(news);
        message.setArticles(newses);
        return message;
    }

    private BaseMessage text(String answer) {
        JSONObject object = JSONObject.parseObject(answer);
        String content = object.getString("text");
        TextMessage message = new TextMessage();
        message.setCreateTime(String.valueOf(new Date().getTime()));
        message.setContent(content);
        message.setMsgType("text");
        return message;
    }

}
