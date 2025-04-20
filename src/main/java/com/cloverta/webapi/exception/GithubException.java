package com.cloverta.webapi.exception;

public class GithubException extends RuntimeException {
    public GithubException(String message) {
        super(message);
    }
}