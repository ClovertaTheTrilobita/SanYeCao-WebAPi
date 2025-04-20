package com.cloverta.webapi.service;

import com.cloverta.webapi.model.BiliVid;
import com.cloverta.webapi.restservice.GithubStars;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class GithubStarsService {
    public GithubStars generateGithubStars(boolean status, String message, int stars){
        if (status) {
            return new GithubStars("OK", message, stars);
        }else {
            return new GithubStars("ERROR", message, stars);
        }
    }

    public GithubStars getGithubStars(String userName) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            //创建连接
            URL url = new URL(String.format("https://api.github.com/users/%s/repos", userName));
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setReadTimeout(15000);
            //开始连接
            connection.connect();
            //获取响应数据
            if (connection.getResponseCode() == 200) {
                //获取返回的数据
                is = connection.getInputStream();
                if (null != is) {
                    br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    String temp = null;
                    while (null != (temp = br.readLine())) {
                        result.append(temp);
                    }
                }
            }

            String jsonResponse = result.toString();
            JSONArray root = new JSONArray(jsonResponse);

            int stars = 0;

            for (Object info : root){
                JSONObject obj = (JSONObject) info;
                if (!obj.getBoolean("fork")){
                    stars += obj.getInt("stargazers_count");
                }
            }

            return generateGithubStars(true, "success", stars);
        } catch (IOException e) {
            e.printStackTrace();
            return generateGithubStars(false, "Cant access data: https://api.github.com unreachable. Please contact cloverta@petalmail.com for further support.", -1);
        }catch (org.json.JSONException e) {
            e.printStackTrace();
            return generateGithubStars(false, "Invalid User Name: Please check whether there was a typo or whether the user exists.", -1);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭远程连接
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
