package com.cloverta.webapi.service;

import com.cloverta.webapi.mapper.StudentMapper;
import com.cloverta.webapi.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentMapper studentMapper;
    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public List<Student> findAll() {
        return studentMapper.findAll();
    }
}
