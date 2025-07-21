package com.school_medical.school_medical_management_system.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class api {
    @GetMapping(value = "/test")
    public String test() {
        return "test 1";
    }
}
