package com.cloverta.webapi.controller;

import com.cloverta.webapi.model.Student;
import com.cloverta.webapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestParam String name,
                                                      @RequestParam(required = false) String email,
                                                      @RequestParam(required = false) String phone) {
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPhone(phone);

        if (studentService.insert(student)) {
            // 成功返回201 Created和资源位置
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", "/students/" + student.getId())
                    .body(Map.of(
                            "status", "success",
                            "message", "学生创建成功",
                            "studentId", student.getId()
                    ));
        } else {
            // 失败返回500错误
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "学生创建失败",
                            "error", "数据库操作失败"
                    ));
        }
    }

    @GetMapping("/search")
    public List<Student> searchById(@RequestParam int id) {
        return studentService.findById(id);
    }
}
