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
import com.pseandroid2.dailydataserver.postDatabase.Request.AddPostParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Controller, which provides all methods for posts.
 * <p>
 * The client my call those methods and get the recommended Data or can publish them
 * <p>
 * This controller is the interface for all interaction which belongs to posts.
 */
@RestController
@RequestMapping("/Posts")
public class PostsController {

    @Autowired
    private final PostService service;


    /**
     * the Constructor for the PostsController
     *
     * @param service the post service, which contains the logic for the methods.
     */
    public PostsController(PostService service) {
        this.service = service;
    }

    /**
     * Provides all PostsPreviews with their PostIds.
     *
     * @return a list with PostPreview, which contains postPreviews and the Post ids.
     */
    @GetMapping("/allPreview")
    public List<PostPreview> getAllPostPreview() {
        return service.getAllPostPreview();
    }

    /**
     * Provides all Templates details from fromPost. Returns the identifier and the DetailView from a template.
     * For every template is declared if it's a project template or not.
     *
     * @param fromPost declares from which post the postDetail is recommended.
     * @return a list of the template Details.
     */
    @GetMapping("/detail/{post}")
    public List<TemplateDetail> getPostDetail(@PathVariable("post") int fromPost) {

        return service.getTemplateDetails(fromPost);
    }

    /**
     * Provides the projectTemplate from the post fromPost
     *
     * @param fromPost declares from which post the projectTemplate is recommended (provided by the client)
     * @return the projectTemplate as JSON
     */
    @GetMapping("/{post}/projectTemplate")
    public String getProjectTemplate(@PathVariable("post") int fromPost) {
        return service.getProjectTemplate(fromPost);
    }

    /**
     * Provides a specified GraphTemplate from a specified post.
     *
     * @param fromPost       declares from which project the graph template is recommended.
     * @param templateNumber declares which template is recommended.
     * @return the GraphTemplate as JSON
     */
    @GetMapping("/{post}/{template}")
    public String getGraphTemplate(@PathVariable("post") int fromPost, @PathVariable("template") int templateNumber) {
        return service.getGraphTemplate(fromPost, templateNumber);
    }

    /**
     * Adds a new Post. If there is more than allowed from the same user, they can not create a new one.
     *
     * @param params the data, which are recommended to add a new post. {@link AddPostParameter} specifies this
     *               parameter.
     * @return the postID of the new post or 0 if the user has too many posts and can't add a new one.
     */
    @PostMapping("/add")
    public int addPost(@RequestAttribute String user, @RequestBody AddPostParameter params) {

        if (service.checkPostAmount(user)) {
            return 0;
        } else {
            return service.addPost(params.getPostPreview(), params.getProjectTemplate(), params.getGraphTemplates(),
                    user);
        }
    }

    /**
     * Removes a post if the user is allowed to remove it.
     *
     * @param user   the user, who wants to remove a post.
     * @param postID which Post should be removed.
     * @return if the post could be removed.
     */
    @DeleteMapping("/remove/{post}")
    public boolean removePost(@RequestAttribute String user, @PathVariable("post") int postID) {
        return service.removePost(postID, user);
    }
}
