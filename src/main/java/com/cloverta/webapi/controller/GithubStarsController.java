package com.cloverta.webapi.controller;

import com.cloverta.webapi.exception.GithubException;
import com.cloverta.webapi.restservice.GithubStars;
import com.cloverta.webapi.service.GithubStarsService;
import com.cloverta.webapi.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubStarsController {

    private final GithubStarsService githubStarsService;

    public GithubStarsController(GithubStarsService githubStarsService) {
        this.githubStarsService = githubStarsService;
    }

    @GetMapping("/github/stars")
    public GithubStars getGithubStars(@RequestParam(value = "user", required = true) String user) {
        try {
            return githubStarsService.getGithubStars(user);
        } catch (Exception e) {
            throw new GithubException(e.getMessage());
        }
    }
}
