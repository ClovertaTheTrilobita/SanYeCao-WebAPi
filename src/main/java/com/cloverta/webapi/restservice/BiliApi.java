package com.cloverta.webapi.restservice;

import com.cloverta.webapi.model.BiliVid;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BiliApi(String status,
                      String message,
                      String bvId,
                      String name,
                      String author,
                      String imageUrl,
                      String description,
                      List<BiliVid> data) {
}
