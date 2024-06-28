package com.devmare.hack4bengal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class AppTestController {


    @GetMapping("/hello")
    public String hello() {
        System.out.printf("Hello");
        return "Get ready for Hack4Bengal!";
    }

    @GetMapping("/bye")
    public String bye() {
        return "Bye!";
    }
}
