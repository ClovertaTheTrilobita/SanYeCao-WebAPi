package com.cloverta.webapi.controller;

import com.cloverta.webapi.mapper.StudentMapper;
import com.cloverta.webapi.model.Student;
import com.cloverta.webapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {
    private StudentService studentService;
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/fetch")
    public List<Student> fetch() {
        return studentService.findAll();
    }
}
