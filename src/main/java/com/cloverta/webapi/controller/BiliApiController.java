package com.cloverta.webapi.controller;

import com.cloverta.webapi.restservice.BiliApi;
import com.cloverta.webapi.service.BiliApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BiliApiController {
    private final BiliApiService biliApiService;

    public BiliApiController(BiliApiService biliApiService) {
        this.biliApiService = biliApiService;
    }

    @GetMapping("/bili")
    public BiliApi BiliApi(@RequestParam(value = "bvid", required = true) String bvid) {
        return biliApiService.getPagesInfo(bvid);
    }
}
