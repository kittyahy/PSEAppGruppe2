package com.pseandroid2.dailydataserver.postDatabase.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * The class contains everything, which is necessary for a Template detail. This information gets a user, before they
 * want to download the template themselves.
 * <p>
 * The class should be used for requests, to provide the template details.
 */
@Getter
@Setter
public class TemplateDetailWrapper {
    private Byte[] templateDetailImage;
    private String title;

    /**
     * The constructor for the TemplateDetailWrapper.
     *
     * @param templateDetailImage the image, which the user should see.
     * @param title               the title of the template.
     */
    public TemplateDetailWrapper(final Byte[] templateDetailImage, final String title) {
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

}
