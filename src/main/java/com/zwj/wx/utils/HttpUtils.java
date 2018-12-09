package com.zwj.wx.utils;

import com.alibaba.fastjson.JSONObject;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: zwj
 * @Date: 12/3/18
 * @Time: 9:02 PM
 * @description:
 */
public class HttpUtils {

    /**
     * 向后台发送post请求
     * @param param
     * @param url
     * @return
     */
    public static String sendPost(String param, String url) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(50000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "token");
            conn.setRequestProperty("tag", "htc_new");

            conn.connect();

            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(param);

            out.flush();
            out.close();
            //
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendGet(String path){

        String result = null;
        BufferedReader in = null;
        try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection","keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0(compatible:MSIE6.0;windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = in.readLine())!=null){
                result += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in !=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
