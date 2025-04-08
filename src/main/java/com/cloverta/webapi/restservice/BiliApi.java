package com.cloverta.webapi.restservice;

import com.cloverta.webapi.model.BiliVid;

import java.util.List;

public record BiliApi(String status,
                      String bvId,
                      String name,
                      String author,
                      String imageUrl,
                      String description,
                      List<BiliVid> data) {
}
