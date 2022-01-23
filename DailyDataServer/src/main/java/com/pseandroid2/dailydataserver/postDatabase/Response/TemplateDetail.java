package com.pseandroid2.dailydataserver.postDatabase.Response;

public class TemplateDetail {
    private int id;
    private String detail;
    private boolean isProjectTemplate;

    public TemplateDetail(int id, String detail, boolean isProjectTemplate) {
        this.id = id;
        this.detail = detail;
        this.isProjectTemplate = isProjectTemplate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isProjectTemplate() {
        return isProjectTemplate;
    }

    public void setProjectTemplate(boolean projectTemplate) {
        isProjectTemplate = projectTemplate;
    }

    @Override
    public String toString() {
        return "TemplateDetail{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                ", isProjectTemplate=" + isProjectTemplate +
                '}';
    }
}
