/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
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
    private Byte[] previewPicture;
    private String title;
    /**
     * The Constructor for the postPreviewWrapper.
     *
     * @param previewPicture the picture, which the user can see, as preview for the whole post.
     * @param title          the title of the post.
     */
    public PostPreviewWrapper(final Byte[] previewPicture, final String title) {
        this.previewPicture = previewPicture;
        this.title = title;
    }


    @Override
    public String toString() {
        return "PostPreviewWrapper{" +
                "PreviewPicture=" + Arrays.toString(previewPicture) +
                ", title='" + title + '\'' +
                '}';
    }
}
