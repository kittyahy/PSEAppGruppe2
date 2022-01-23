/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
package com.pseandroid2.dailydataserver.postDatabase;

import com.pseandroid2.dailydataserver.postDatabase.Response.PostPreview;
import com.pseandroid2.dailydataserver.postDatabase.Response.TemplateDetail;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private static final int MAX_POSTS = 5;
    private final TemplateRepository tempRepo;
    private final PostsRepository postRepo;
    private int postId;

    public PostService(PostsRepository postRepo, TemplateRepository tempRepo) {
        this.postRepo = postRepo;
        this.tempRepo = tempRepo;
        postId = 1;
    }

    /**
     * @return PostPreviews
     */
    List<PostPreview> getAllPostPreview() {
        List<Posts> postList = postRepo.findAll();
        List<PostPreview> returnList = new ArrayList<>();

        for (Posts post : postList) {
            returnList.add(
                    new PostPreview(post.getPostId(), post.getPostPreview()));
        }
        return returnList;
    }

    public int addPost(String postPreview, Pair<String, String> projectTemplate, List<Pair<String, String>> graphTemplates, String user) {
        Posts post = new Posts(postId, postPreview, user);
        postRepo.save(post);
        postId++;
        Template t = new Template(post.getPostId(), post.getTemplateIds(), projectTemplate.getFirst(), true, projectTemplate.getSecond());
        tempRepo.save(t);
        post.increaseTemplateIds();

        for (Pair<String, String> template :
                graphTemplates) {
            tempRepo.save(new Template(post.getPostId(), post.getTemplateIds(), template.getFirst(), false, template.getSecond()));
            post.increaseTemplateIds();

        }
        return post.getPostId();
    }


    public boolean checkPostAmount(String user) {
        //#TODO;
        return false;
    }

    public List<TemplateDetail> getTemplateDetailsAndID(int post) {
        Posts recommendedPost = postRepo.findById(post).get();
        //#TODO sanatiy check

        List<Template> templatesFromPost = new ArrayList<>(tempRepo.findByPost(recommendedPost.getPostId()));
        List<TemplateDetail> detailList = new ArrayList<>();
        for (Template tem : templatesFromPost) {
            detailList.add(new TemplateDetail(tem.getTemplateNumber(), tem.getDetailView(), tem.isProjectTemplate()));
        }
        return detailList;
    }

    public String getProjectTemplate(int postId) {
        return tempRepo.findByPostAndIsProjectTemplateIsTrue(postId).getTemplateInitial();
    }

    public String getGraphTemplate(int postId, int tempNr) {
        Template tem =  tempRepo.findById(new TemplateId(postId, tempNr)).get();
        return tem.getTemplateInitial();
    }

    public boolean removePost(int postId, String user){
        if(!user.equals(postRepo.findById(postId).get().getCreatedBy())){
            return false;
        }
        tempRepo.deleteByPost(postId);
        postRepo.deleteById(postId);
        return true;
    }
}
