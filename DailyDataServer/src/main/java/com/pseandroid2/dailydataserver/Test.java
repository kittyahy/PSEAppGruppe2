package com.pseandroid2.dailydataserver;

import com.pseandroid2.dailydataserver.postDatabase.Post;
import com.pseandroid2.dailydataserver.postDatabase.PostsRepository;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class Test {
    private PostsRepository pr;

    @Deprecated(since = "Only for testing")
    public Test(PostsRepository p){
        pr = p;
    }

    @Deprecated(since = "Only for testing")
    @RequestMapping("/test")
    public List<Integer> test(@RequestAttribute String user){
        List<Post> postList = pr.findByCreatedBy(user);
        List<Integer> ids = new ArrayList<>();
        for(Post o : postList){
            ids.add(o.postId);
        }
        return ids;
    }
}
