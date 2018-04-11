package com.github.sjacek.swagger_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.sjacek.swagger_demo.config.SwaggerConfig.V01;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping(value = "/" + V01, produces = TEXT_PLAIN_VALUE)
public class HelloController {

    @GetMapping("/hello")
    String hello() {
        return "Hello, world!";
    }
}
