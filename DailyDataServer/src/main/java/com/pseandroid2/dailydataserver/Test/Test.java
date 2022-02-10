package com.pseandroid2.dailydataserver.Test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Deprecated(since = "Only for testing")
@RestController
@RequestMapping("/test")
public class Test {
    TestService pr;
    @Deprecated(since = "Only for testing")

    public Test(TestService p) {
        pr = p;
    }

    @Deprecated(since = "Only for testing")
    @GetMapping("/allPosts")
    public List<Integer> test(@RequestAttribute String user) {

        return pr.getAllPostFromUser(user);
    }
    @Deprecated(since = "Only for testing")

    @DeleteMapping("/deleteAll")
    public boolean clearAll (){
        return pr.deleteAll();
    }


}
