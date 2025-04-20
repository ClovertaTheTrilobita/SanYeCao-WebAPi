package com.cloverta.webapi.controller;

import com.cloverta.webapi.restservice.Error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public Error error(HttpServletRequest request) {
        String message = (String) request.getAttribute("javax.servlet.error.message");
        if (message == null) {
            message = "Something went wrong...";
        }
        return new Error("ERROR", message);
    }
}
