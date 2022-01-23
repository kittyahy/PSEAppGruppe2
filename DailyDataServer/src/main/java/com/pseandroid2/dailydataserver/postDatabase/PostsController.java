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

import com.pseandroid2.dailydataserver.Communication.RequestParameter;
import com.pseandroid2.dailydataserver.postDatabase.Response.PostPreview;
import com.pseandroid2.dailydataserver.postDatabase.Response.TemplateDetail;
import com.pseandroid2.dailydataserver.postDatabase.requestparameters.AddPostParameter;
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
 * #TODO Testen
 */

/**
 * Interface for all interaction for Posts
 */
@RestController
@RequestMapping("/Posts")
public class PostsController {

    private PostService service;


    public PostsController(PostService service) {
        this.service = service;
    }

    /**
     * Provides all PostsPreviews with their PostIds.
     *
     * @param param the data, for authenticate the user. {@link RequestParameter} specifies this parameter.
     * @return a list with all PostPreview and the Post ids.
     */
    @GetMapping("/allPreview")
    public List<PostPreview> getAllPostPreview(@RequestBody RequestParameter param) {
        return service.getAllPostPreview();
    }

    /**
     * Provides all Templates detail view from fromPost. Returns the identifier and the DetailView from a template
     *
     * @param fromPost declares from which post the postDetail is recommended (provided by the client)
     * @param param    the data, for authenticate the user. {@link RequestParameter} specifies this parameter.
     * @return a list of all template detailViews with TemplateNumber together, each as String.
     */
    @GetMapping("/detail/{post}")
    public List<TemplateDetail> getPostDetail(@PathVariable("post") int fromPost, @RequestBody RequestParameter param) {

        return service.getTemplateDetailsAndID(fromPost);
    }

    /**
     * Provides the projectTemplate from the post fromPost
     *
     * @param fromPost declares from which post the projectTemplate is recommended (provided by the client)
     * @param param    the data, for authenticate the user. {@link RequestParameter} specifies this parameter.
     * @return the projectTemplate as JSON
     */
    @GetMapping("/{post}/projectTemplate")
    public String getProjectTemplate(@PathVariable("post") int fromPost, @RequestBody RequestParameter param) {
        return service.getProjectTemplate(fromPost);
    }

    /**
     * Provides a specified GraphTemplate.
     *
     * @param fromPost       declares from which project the graph template is recommended (provided by the client)
     * @param templateNumber declares which template is recommended (provided by the client)
     * @param param          the data, for authenticate the user. {@link RequestParameter} specifies this parameter.
     * @return the GrapgTemplate as JSON
     */
    @GetMapping("/{post}/{template}")
    public String getGraphTemplate(@PathVariable("post") int fromPost, @PathVariable("template") int templateNumber, @RequestBody RequestParameter param) {
        return service.getGraphTemplate(fromPost, templateNumber);
    }

    /**
     * adds a new Post. If there is more than 5 from the same user, have to delete the oldest one.
     *
     * @param params the data, which are recommended to add a new post. {@link AddPostParameter} specifies this parameter.
     * @return the postID of the new post or 0 if the user has to much posts and can't add a new one.
     */
    @PostMapping("/add")
    public int addPost(@RequestAttribute String user, @RequestBody AddPostParameter params) {

        if (service.checkPostAmount(user)) {
            return 0;
        } else {
            return service.addPost(params.getPostPreview(), params.getProjectTemplate(), params.getGraphTemplates(), user);
        }
    }

    /**
     * Removes a post if the user is allowed to remove it.
     *
     * @param user   the user, who wants to remove a post. Generated by the client.
     * @param postID which Post should be removed.
     * @param param  the data, for authenticate the user. {@link RequestParameter} specifies this parameter.
     * @return if the post could be removed.
     */
    @DeleteMapping("/remove/{post}")
    public boolean removePost(@RequestAttribute String user, @PathVariable("post") int postID, @RequestBody RequestParameter param) {
        return service.removePost(postID, user);
    }


}
