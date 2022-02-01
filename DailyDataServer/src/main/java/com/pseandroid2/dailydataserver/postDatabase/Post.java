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

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Post is an Entity, which contains all information to a post. The templateIDs contains the next id for a new graph
 * or project Template.
 */
@Getter
@Setter
@Entity
@Table(name = "Post_Table")
public class Post {
    @Id
    public int postId;
    private String postPreview;
    private String createdBy;

    private int templateIds;

    /**
     * The constructor, which should be used, to create a new Post. The templateIds is initialized with 0, because
     * they start without any templates.
     *
     * @param postId      the id, which belongs to the post.
     * @param postPreview the postPreview, which identifies the post
     * @param createdBy   the user, who upload the post.
     */
    public Post(int postId, String postPreview, String createdBy) {
        this.postId = postId;
        this.postPreview = postPreview;
        this.createdBy = createdBy;
        templateIds = 0;
    }

    /**
     * The recommended empty constructor
     */
    public Post() {

    }


    /**
     * This method increases the templateIDs for one. it can be used, after a new template for this post gets created.
     */
    public void increaseTemplateIds() {
        templateIds++;
    }

    @Override
    public String toString() {
        return "Post{" + "postId=" + postId + ", postPreview='"
                + postPreview + '\'' + ", createdBy='" + createdBy + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return postId == post.postId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }
}
