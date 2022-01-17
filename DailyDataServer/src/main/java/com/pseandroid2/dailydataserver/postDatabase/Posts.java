package com.pseandroid2.dailydataserver.postDatabase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="Post_Table")
public class Posts {
    private @Id
    @GeneratedValue int postId;
    private String postPreview;
    private String createdBy;

    public Posts(int postId, String postPreview, String createdBy) {
        this.postId = postId;
        this.postPreview = postPreview;
        this.createdBy = createdBy;
    }

    public Posts() {

    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostPreview() {
        return postPreview;
    }

    public void setPostPreview(String postPreview) {
        this.postPreview = postPreview;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "postId=" + postId +
                ", postPreview='" + postPreview + '\'' +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posts posts = (Posts) o;
        return postId == posts.postId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }
}
