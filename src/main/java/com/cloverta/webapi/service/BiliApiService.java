package com.cloverta.webapi.service;

import com.cloverta.webapi.model.AppSigner;
import com.cloverta.webapi.model.BiliVid;
import com.cloverta.webapi.restservice.BiliApi;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class BiliApiService {
    public BiliApi getPagesInfo(String bvid) {
        if (bvid.equals("empty")) {
            return generateBiliApi("ERROR", "Missing param 'bvid'", null, null, null, null, null, null);
        }
        List<BiliVid> biliVids = new ArrayList<>();
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            //创建连接
            URL url = new URL(String.format("https://api.bilibili.com/x/web-interface/view?bvid=%s", bvid));
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
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject data;

                data = root.getJSONObject("data");

            String bvId = data.getString("bvid");
            String name = data.getString("title");
            String author = data.getJSONObject("owner").getString("name");
            String imageUrl = data.getString("pic");
            String description = data.optString("desc");

            JSONArray pages = data.getJSONArray("pages");
            // System.out.println(pages.toString());

            for (int i = 0; i < pages.length(); i++) {
                JSONObject page = pages.getJSONObject(i);
                BiliVid biliVid = new BiliVid();
                Long cid = page.getLong("cid");
                String part = page.getString("part");
                int duration = page.getInt("duration");

                biliVid.setTitle(part);
                biliVid.setCid(cid);
                biliVid.setDuration(duration);
                biliVid.setBvId(bvid);

                biliVids.add(biliVid);
//                System.out.println(biliVid.getBvId());
//                System.out.println(biliVid.getCid());
            }

            return generateBiliApi("OK", "success", bvId, name, author, imageUrl, description, appendVidUrl(biliVids));
        } catch (IOException e) {
            e.printStackTrace();
            return generateBiliApi("ERROR", "Can't access data: api.bilibili.com unreachable. Please contact cloverta@petalmail.com for further support.", null, null, null, null, null, null);
        }catch (org.json.JSONException e) {
            e.printStackTrace();
            return generateBiliApi("ERROR", "Invalid BVId: Please check whether there was a typo or whether the video exists.", null, null, null, null, null, null);
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

    public List<BiliVid> appendVidUrl(List<BiliVid> biliVids) {
        for (BiliVid biliVid : biliVids) {
            HttpURLConnection connection = null;
            InputStream is = null;
            BufferedReader br = null;
            StringBuilder result = new StringBuilder();
            try {
                Map<String, String> params = new HashMap<>();
                params.put("bvid", biliVid.getBvId() );
                params.put("cid", String.valueOf(biliVid.getCid()));
                //创建连接
                URL url = new URL(String.format("https://api.bilibili.com/x/player/wbi/playurl?bvid=%s&cid=%s&platform=html5&appkey=1d8b6e7d45233436&sign=%s&qn=64", biliVid.getBvId(), biliVid.getCid(), AppSigner.appSign(params)));
                connection = (HttpURLConnection) url.openConnection();
                //设置请求方式
                connection.setRequestMethod("GET");

                // 添加防盗链需要的请求头
                connection.setRequestProperty("Referer", "https://www.bilibili.com");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
                // 设置连接超时时间
                connection.setReadTimeout(15000);
                // 开始连接
                connection.connect();
                // 获取响应数据
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
                // System.out.println(result);
                JSONObject root = new JSONObject(jsonResponse);

                JSONObject data = root.getJSONObject("data");
                // System.out.println(data);
                JSONArray durl = data.getJSONArray("durl");
                String vidUrl = durl.getJSONObject(0).getString("url");

                biliVid.setUrl(vidUrl);
            } catch (IOException e) {
                e.printStackTrace();
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
        return biliVids;
    }

    public BiliApi generateBiliApi(String status,
                                          String message,
                                          String bvId,
                                          String name,
                                          String author,
                                          String imageUrl,
                                          String description,
                                          List<BiliVid> data) {
        return new BiliApi(status, message, bvId, name, author, imageUrl, description, data);
    }

//    public static void main(String[] args) {
//        System.out.println(getPagesInfo("BV117411r7R1").data().get(0).getUrl());
//    }
}
