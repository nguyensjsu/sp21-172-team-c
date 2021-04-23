package com.example.springstarbucksapi;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class PingController {

    class Ping {
        private String test;

        public Ping(String message) {
            this.test = message;
        }

        public String getTest() {
            return this.test;
        }
    }

    @GetMapping("/ping")
    public Ping ping() {
        return new Ping("Starbucks API version 1.0 alive!");
    }
}