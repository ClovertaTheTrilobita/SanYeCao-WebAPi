package com.cloverta.webapi.service;

import com.cloverta.webapi.mapper.ApiMapper;
import com.cloverta.webapi.model.Api;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiListService {
    private final ApiMapper apiMapper;

    public ApiListService(ApiMapper apiMapper) {
        this.apiMapper = apiMapper;
    }

    public List<Api> generateApiList() {
        return apiMapper.findAll();
    }
}
