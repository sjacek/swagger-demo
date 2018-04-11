package com.github.sjacek.swagger_demo.client.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class Hello {

    @GetMapping("/hello")
    @ResponseBody
    String hello() {
        return "Hello!";
    }
}
