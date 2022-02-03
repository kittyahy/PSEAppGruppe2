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
package com.pseandroid2.dailydataserver.postDatabase.Response;

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
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * A class, which contains an image, the title and the id of the post, where the postPreview belongs to.
 * Serves as Response object for  getAllPostPreview.
 * <p>
 * The class exists, because not the whole Post entity should be returned.
 */
@Setter
@Getter
public class PostPreview {
    private int id;
    private String preview;
    private Byte[] previewImage;


    /**
     * The constructor, to create an object, which contains a postPreview and the corresponding id.
     *  @param id      the id from the post entity.
     * @param previewImage
     * @param preview the recommended postPreview.
     */
    public PostPreview(int id, Byte[] previewImage, String preview) {
        this.id = id;
        this.preview = preview;
        this.previewImage = previewImage;
    }

    @Override
    public String toString() {
        return "PostPreview{" +
                "id=" + id +
                ", preview='" + preview + '\'' +
                ", previewImage=" + Arrays.toString(previewImage) +
                '}';
    }
}
