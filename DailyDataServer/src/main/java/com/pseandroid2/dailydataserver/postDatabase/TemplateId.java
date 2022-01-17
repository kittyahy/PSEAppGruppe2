package com.pseandroid2.dailydataserver.postDatabase;

import javax.persistence.Id;
import java.io.Serializable;

public class TemplateId implements Serializable {
    private Posts post;
    private int templateNumber;

    public TemplateId(Posts post, int templateNumber) {
        this.post = post;
        this.templateNumber = templateNumber;
    }

    public TemplateId() {
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public int getTemplateNumber() {
        return templateNumber;
    }

    public void setTemplateNumber(int templateNumber) {
        this.templateNumber = templateNumber;
    }
}
