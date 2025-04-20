package com.cloverta.webapi.controller;

import com.cloverta.webapi.restservice.BiliApi;
import com.cloverta.webapi.service.BiliApiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ResponseStatus(HttpStatus.OK)
@RestController
public class BiliApiController {
    private final BiliApiService biliApiService;

    public BiliApiController(BiliApiService biliApiService) {
        this.biliApiService = biliApiService;
    }

    @GetMapping("/bili")
    public BiliApi BiliApi(@RequestParam(value = "bvid", defaultValue = "empty") String bvid) {
        return biliApiService.getPagesInfo(bvid);
    }
}
