package com.pseandroid2.dailydataserver.postDatabase.Response;

public class PostPreview {
    private int id;
    private String preview;



    public PostPreview(int id, String templateDetail){
        this.id = id;
        this.preview = templateDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    @Override
    public String toString() {
        return "PostPreview{" +
                "id=" + id +
                ", preview='" + preview + '\'' +
                '}';
    }
}
