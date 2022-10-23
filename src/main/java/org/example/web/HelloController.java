package org.example.web;

import lombok.Getter;
import org.example.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")   // localhost:8080/hello/dto?name=cje&amount=1
    public HelloResponseDto helloResponseDto(@RequestParam String name, @RequestParam int amount){
        return new HelloResponseDto(name, amount);
    }
}
