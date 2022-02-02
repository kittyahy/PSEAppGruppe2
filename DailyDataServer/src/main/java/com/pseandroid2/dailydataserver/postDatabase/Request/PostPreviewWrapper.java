package com.pseandroid2.dailydataserver.postDatabase.Request;


import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * This method contains everything, what is necessary fpr the post Preview.
 * It is used for a request, to get a post preview.
 */
@Getter
@Setter
public class PostPreviewWrapper {
    private byte[] PreviewPicture;
    private String title;

    /**
     * The Constructor for the postPreviewWrapper.
     *
     * @param previewPicture the picture, which the user can see, as preview for the whole post.
     * @param title          the title of the post.
     */
    public PostPreviewWrapper(final byte[] previewPicture, final String title) {
        PreviewPicture = previewPicture;
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
