package com.cloverta.webapi.controller;

import com.cloverta.webapi.model.Api;
import com.cloverta.webapi.service.ApiListService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ResponseStatus(HttpStatus.OK)
@RestController
public class ApiListController {
    private final ApiListService apiListService;

    public ApiListController(ApiListService apiListService) {
        this.apiListService = apiListService;
    }

    @RequestMapping("/list")
    public List<Api> getApiLists() {
        return apiListService.generateApiList();
    }
}
