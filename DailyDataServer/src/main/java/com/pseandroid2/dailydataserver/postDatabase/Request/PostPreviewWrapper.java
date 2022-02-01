package com.pseandroid2.dailydataserver.postDatabase.Request;


import java.util.Arrays;

public class PostPreviewWrapper {
  private  byte[] PreviewPicture;
  private   String title;

    public PostPreviewWrapper(final byte[] previewPicture, final String title) {
        PreviewPicture = previewPicture;
        this.title = title;
    }

    public byte[] getPreviewPicture() {
        return PreviewPicture;
    }

    public void setPreviewPicture(final byte[] previewPicture) {
        PreviewPicture = previewPicture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "PostPreviewWrapper{" +
                "PreviewPicture=" + Arrays.toString(PreviewPicture) +
                ", title='" + title + '\'' +
                '}';
    }
}
