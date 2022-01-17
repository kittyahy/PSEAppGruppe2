package com.pseandroid2.dailydataserver.postDatabase;

import javax.persistence.*;
import java.util.Objects;

/**
 * Wie kommt man an die Templateid's?
 */
@Entity
@Table(name="Template_Table")
@IdClass(TemplateId.class)
public class Template {

    @ManyToOne
    private @Id Posts post;
    private @Id int templateNumber;
    private String templateInitial;
    private boolean isProjectTemplate;
    private String detailView;

    public Template(Posts post, int templateNumber, String templateInitial, boolean isProjectTemplate, String detailView) {
        this.post = post;
        this.templateNumber = templateNumber;
        this.templateInitial = templateInitial;
        this.isProjectTemplate = isProjectTemplate;
        this.detailView = detailView;
    }

    public Template() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return templateNumber == template.templateNumber && post.equals(template.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, templateNumber);
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

    public String getTemplateInitial() {
        return templateInitial;
    }

    public void setTemplateInitial(String templateInitial) {
        this.templateInitial = templateInitial;
    }

    public boolean isProjectTemplate() {
        return isProjectTemplate;
    }

    public void setProjectTemplate(boolean projectTemplate) {
        isProjectTemplate = projectTemplate;
    }

    public String getDetailView() {
        return detailView;
    }

    public void setDetailView(String detailView) {
        this.detailView = detailView;
    }
}
