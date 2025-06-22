package com.cloverta.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WebapiApplication {

    @RequestMapping("/")
    String home(){
        return "Welcome to Cloverta's WebAPI✨, please visit <a href='https://cloverta.top'>https://cloverta.top</a> for more information.";
    }

    public static void main(String[] args) {
        SpringApplication.run(WebapiApplication.class, args);
    }

}
