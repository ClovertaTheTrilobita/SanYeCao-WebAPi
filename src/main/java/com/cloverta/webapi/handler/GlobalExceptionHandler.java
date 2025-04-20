package com.cloverta.webapi.handler;

import com.cloverta.webapi.exception.GithubException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GithubException.class)
    public String handleGithubException(GithubException ex, HttpServletRequest request) {
        request.setAttribute("javax.servlet.error.message", ex.getMessage());
        return "forward:/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, HttpServletRequest request) {
        request.setAttribute("javax.servlet.error.message", ex.getMessage());
        return "forward:/error";
    }
}