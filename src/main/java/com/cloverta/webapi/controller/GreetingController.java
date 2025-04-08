package com.cloverta.webapi.controller;

import com.cloverta.webapi.restservice.Greeting;
import com.cloverta.webapi.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    // 显式声明构造方法（Spring 4.3+ 可省略 @Autowired）
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/greeting")
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "World") String name,
            @RequestParam(value = "test", defaultValue = "!") String test) {

        // 调用 Service 处理核心逻辑
        return greetingService.generateGreeting(name, test);
    }
}
