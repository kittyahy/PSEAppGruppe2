package com.pseandroid2.dailydataserver.posts.requestparameters;

import com.pseandroid2.dailydataserver.RequestParameter;

import java.util.ArrayList;
import java.util.List;

public class AddPostParameter extends RequestParameter {

    private String postPreview;
    private String projectTemplate;
    private List<String> graphTemplates;

    public AddPostParameter(String token, String postPreview, String projectTemplate, List<String> graphTemplates) {
        super(token);
        this.postPreview = postPreview;
        this.projectTemplate = projectTemplate;
        this.graphTemplates.addAll(graphTemplates);
    }


    public String getPostPreview() {
        return postPreview;
    }

    public void setPostPreview(String postPreview) {
        this.postPreview = postPreview;
    }

    public String getProjectTemplate() {
        return projectTemplate;
    }

    public void setProjectTemplate(String projectTemplate) {
        this.projectTemplate = projectTemplate;
    }

    public List<String> getGraphTemplates() {
        List<String> list = new ArrayList<>();
        list.addAll(graphTemplates);
        return list;
    }

    public void setGraphTemplates(List<String> graphTemplates) {
        this.graphTemplates.addAll(graphTemplates);
    }

    @Override
    public String toString() {
        return "AddPostParameter{" +
                "postPreview='" + postPreview + '\'' +
                ", projectTemplate='" + projectTemplate + '\'' +
                ", graphTemplates=" + graphTemplates.toString() +
                "} " + super.toString();
    }
}
