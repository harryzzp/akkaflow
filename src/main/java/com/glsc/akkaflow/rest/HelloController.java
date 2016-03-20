package com.glsc.akkaflow.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/say")
    public String say(@RequestParam(value = "name", required = false, defaultValue = "Jedi") String name) {
        return name + ", greeting from Spring Boot!";
    }
    
}
