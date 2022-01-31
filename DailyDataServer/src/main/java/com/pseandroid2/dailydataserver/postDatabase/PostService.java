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


/**
 * Logic for the PostsController.
 * <p>
 * The PostService provides methods, to create and delete Posts, get recommended Templates and previews. It also
 * handles the postIds.
 */
@Service
public class PostService {

    private static final int MAX_POSTS = 5;
    private final TemplateRepository tempRepo;
    private final PostsRepository postRepo;
    private int postId;

    /**
     * Creates a postService Constructor.
     *
     * @param postRepo a postRepository, to access the Posts_Table.
     * @param tempRepo a template Repository, to access the Template_Table
     */
    public PostService(PostsRepository postRepo, TemplateRepository tempRepo) {
        this.postRepo = postRepo;
        this.tempRepo = tempRepo;
        postId = 1;
    }

    /**
     * Provides all Post previews with ids as PostPreview
     *
     * @return a list of all available PostPreviews
     */
    List<PostPreview> getAllPostPreview() {
        List<Post> postList = postRepo.findAll();
        List<PostPreview> returnList = new ArrayList<>();

        for (Post post : postList) {
            returnList.add(
                    new PostPreview(post.getPostId(), post.getPostPreview()));
        }
        return returnList;
    }


    /**
     * Adds a new post to the Post_Table, regardless of the amount of posts a user already had done.
     *
     * @param postPreview     the postPreview for the post.
     * @param projectTemplate the projectTemplate for the post. The first is the template itself, the second is the
     *                        detailView.
     * @param graphTemplates  a list of graph Templates. The first entry of en entity is the template, the second is
     *                        the detailView.(could be an empty list)
     * @param user            the user how wants to save a new post.
     * @return the post id of the new Post
     */
    public int addPost(String postPreview, Pair<String, String> projectTemplate,
                       List<Pair<String, String>> graphTemplates, String user) {

        Post post = new Post(postId, postPreview, user);
        postRepo.save(post);
        postId++;

        tempRepo.save(new Template(post.getPostId(), post.getTemplateIds(), projectTemplate.getFirst(), true,
                projectTemplate.getSecond()));
        post.increaseTemplateIds();

        for (Pair<String, String> template : graphTemplates) {

            tempRepo.save(new Template(post.getPostId(), post.getTemplateIds(), template.getFirst(), false,
                    template.getSecond()));
            post.increaseTemplateIds();

        }
        return post.getPostId();
    }


    /**
     * This method checks, if a user doesn't have more than a certain amount of posts
     *
     * @param user the user, from whom the posts should be checked
     * @return true, if the user has less than the critical number of Posts.
     */
    public boolean checkPostAmount(String user) {
        return (postRepo.countByCreatedBy(user) >= MAX_POSTS);
    }

    /**
     * Provides the template Details, their ids and if its a project template.
     * <p>
     * This method does not check: if the post exists.
     *
     * @param post from which post the details are recommended.
     * @return the list of Template Details.
     */
    public List<TemplateDetail> getTemplateDetails(int post) {
        Post recommendedPost = postRepo.findById(post).get();

        List<Template> templatesFromPost = new ArrayList<>(tempRepo.findByPost(recommendedPost.getPostId()));
        List<TemplateDetail> detailList = new ArrayList<>();
        for (Template tem : templatesFromPost) {
            detailList.add(new TemplateDetail(tem.getTemplateNumber(), tem.getDetailView(), tem.isProjectTemplate()));
        }
        return detailList;
    }

    /**
     * Provides the recommended project Template.
     * <p>
     * This method does not check: if the post exists.
     *
     * @param postId the post, from which the project template is recommended.
     * @return the project template.
     */
    public String getProjectTemplate(int postId) {
        return tempRepo.findByPostAndIsProjectTemplateIsTrue(postId).getTemplateInitial();
    }

    /**
     * Provides a specified graph template from a specified project.
     * <p>
     * This method does not check: if the recommended template is a project template, if the post exists, if the
     * template exists.
     *
     * @param postId the post, to which  the template belongs.
     * @param tempNr the template number, from the graphtemplate, which is recommended.
     * @return
     */
    public String getGraphTemplate(int postId, int tempNr) {
        Template tem = tempRepo.findById(new TemplateId(postId, tempNr)).get();
        return tem.getTemplateInitial();
    }

    /**
     * It deletes a Post, if the user has created it.
     * <p>
     * This method does not check: if the post exists.
     *
     * @param postId the post which should be removed.
     * @param user   the user who wants to remove the post.
     * @return true, if the post can be removed, false, if it was not possible
     */
    public boolean removePost(int postId, String user) {
        if (!user.equals(postRepo.findById(postId).get().getCreatedBy())) {
            return false;
        }
        List<Template> listToDelete = tempRepo.findByPost(postId);
        tempRepo.deleteAll(listToDelete);
        postRepo.deleteById(postId);
        return true;
    }
}
