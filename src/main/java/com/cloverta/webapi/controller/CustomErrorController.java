package com.cloverta.webapi.controller;

import com.cloverta.webapi.restservice.Error;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public Error error() {
        return new Error("ERROR", "Something went wrong... I hope it wasn't my fault.");
    }
}
