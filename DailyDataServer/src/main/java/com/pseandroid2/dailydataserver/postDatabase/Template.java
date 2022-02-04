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
package com.pseandroid2.dailydataserver.postDatabase;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Objects;

/**
 * Template is an Entity, which represents a Template with its information.
 * <p>
 * The primary key consists of to parts, because the template number must only be unique in a post. Together the post
 * and the templateNumber define a Template.
 * It is solved by an idClass, because, there are more cases, where only the post is relevant than the whole primary
 * key.
 */
@Getter
@Setter
@Entity
@Table(name = "Template_Table")
@IdClass(TemplateId.class)
public class Template {

    private @Id
    int post;
    private @Id
    int templateNumber;
    private String templateInitial;
    private boolean isProjectTemplate;
    private String detailViewTitle;
    private Byte[] detailViewImage;

    /**
     * The constructor, which should be used to create a template.
     *
     * @param post              defines the post, to which the template belongs.
     * @param templateNumber    defines the unique number, within the post, of the template.
     * @param templateInitial   the project or graph Template themselves, which can be understood by the client.
     * @param isProjectTemplate declares, if this template contains a project template or not.
     * @param detailViewTitle   the title of the detailView, which is going to be presented to the user.
     * @param detailViewImage   the image, which the user can see, before they download the template.
     */
    public Template(int post, int templateNumber, String templateInitial, boolean isProjectTemplate,
                    String detailViewTitle, Byte[] detailViewImage) {
        this.post = post;
        this.templateNumber = templateNumber;
        this.templateInitial = templateInitial;
        this.isProjectTemplate = isProjectTemplate;
        this.detailViewTitle = detailViewTitle;
        this.detailViewImage = detailViewImage;
    }

    /**
     * the recommended empty constructor
     */
    public Template() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return templateNumber == template.templateNumber && post == template.post;
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, templateNumber);
    }

    @Override
    public String toString() {
        return "Template{" +
                "post=" + post +
                ", templateNumber=" + templateNumber +
                ", templateInitial='" + templateInitial + '\'' +
                ", isProjectTemplate=" + isProjectTemplate +
                ", detailViewTitle='" + detailViewTitle + '\'' +
                ", detailViewImage=" + Arrays.toString(detailViewImage) +
                '}';
    }


}
