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
