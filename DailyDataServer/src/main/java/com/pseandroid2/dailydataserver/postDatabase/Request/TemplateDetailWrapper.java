package com.pseandroid2.dailydataserver.postDatabase.Request;

import java.util.Arrays;

public class TemplateDetailWrapper {
   private byte[] templateDetailImage;
   private String title;

    public TemplateDetailWrapper(final byte[] templateDetailImage, final String title) {
        this.templateDetailImage = templateDetailImage;
        this.title = title;
    }

    @Override
    public String toString() {
        return "TemplateDetailWrapper{" +
                "templateDetailWrapper=" + Arrays.toString(templateDetailImage) +
                ", title='" + title + '\'' +
                '}';
    }

    public byte[] getTemplateDetailImage() {
        return templateDetailImage;
    }

    public void setTemplateDetailImage(final byte[] templateDetailImage) {
        this.templateDetailImage = templateDetailImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
