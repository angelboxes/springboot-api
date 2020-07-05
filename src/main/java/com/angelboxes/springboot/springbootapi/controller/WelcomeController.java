package com.angelboxes.springboot.springbootapi.controller;

import com.angelboxes.springboot.springbootapi.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    WelcomeService welcomeService;

    @GetMapping("/welcome")
    public String welcome(){
        return welcomeService.retrieveWelcomeMessage();
    }

}
