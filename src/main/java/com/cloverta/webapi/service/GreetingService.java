package com.cloverta.webapi.service;

import com.cloverta.webapi.restservice.Greeting;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service // Spring 注解，标识为业务层组件
public class GreetingService {
    private final AtomicLong counter = new AtomicLong();

    public Greeting generateGreeting(String name, String test) {
        int j = calculateJ();
        String content = String.format("Hello, %s! %s, You found this eastern egg, well done! Check out the id above to see how many times this page was found", name, test);
        return new Greeting(counter.incrementAndGet(), content);
    }

    private int calculateJ() {
        int j = 0;
        for (int i = 0; i < 10; i++) {
            j++;
        }
        return j;
    }
}
