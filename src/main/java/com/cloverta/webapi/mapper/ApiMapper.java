package com.cloverta.webapi.mapper;

import com.cloverta.webapi.model.Api;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface ApiMapper {

    @Select("SELECT * FROM api_list")
    List<Api> findAll();

}
