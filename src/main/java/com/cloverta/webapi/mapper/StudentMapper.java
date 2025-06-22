package com.cloverta.webapi.mapper;

import com.cloverta.webapi.model.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<Student> findAll();
}
