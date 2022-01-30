package com.pseandroid2.dailydataserver;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/test")
public class TestsController {

    @GetMapping("/token")
    public String test(@RequestHeader String token){

        return "token";
    }

    @GetMapping("/username")
    public String name(@RequestHeader String token, @RequestAttribute String user){
        return user;
    }



}