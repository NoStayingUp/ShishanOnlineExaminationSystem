package com.feidian.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class hellocontroller {

    @GetMapping("/hello")
    public void hello(){
        log.info("hello");
        System.out.println("你好，springboot");
    }
}
