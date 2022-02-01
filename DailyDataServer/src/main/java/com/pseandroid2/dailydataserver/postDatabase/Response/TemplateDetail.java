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

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;


/**
 * A class, which contains the image, the title and the id of the Template, where the templateDetail belongs to. It also
 * contains if the template is a projectTemplate. If not, it's a graph template.
 * <p>
 * The class exists, because not the whole Post entity should be returned.
 */
@Setter
@Getter
public class TemplateDetail {
    private int id;
    private String title;
    private boolean isProjectTemplate;
    private byte[] detailImage;

    /**
     *
     *
     * @param id
     * @param title
     * @param isProjectTemplate
     *
     */
    /**
     * The constructor for the Template details.
     *
     * @param id                The ID of the Template Entity, to which the title belongs.
     * @param title             The title of a given template preview.
     * @param detailImage       An image for the template preview.
     * @param isProjectTemplate If true, this template is a project template, if false, it's a graph template.
     */
    public TemplateDetail(int id, String title, byte[] detailImage, boolean isProjectTemplate) {
        this.id = id;
        this.title = title;
        this.isProjectTemplate = isProjectTemplate;
        this.detailImage = detailImage;
    }


    @Override
    public String toString() {
        return "TemplateDetail{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isProjectTemplate=" + isProjectTemplate +
                ", detailImage=" + Arrays.toString(detailImage) +
                '}';
    }
}
