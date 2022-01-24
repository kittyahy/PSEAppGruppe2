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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Wie kommt man an die Templateid's?
 */
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
    private String detailView;

    public Template(int post, int templateNumber, String templateInitial, boolean isProjectTemplate, String detailView) {
        this.post = post;
        this.templateNumber = templateNumber;
        this.templateInitial = templateInitial;
        this.isProjectTemplate = isProjectTemplate;
        this.detailView = detailView;
    }

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

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public int getTemplateNumber() {
        return templateNumber;
    }

    public void setTemplateNumber(int templateNumber) {
        this.templateNumber = templateNumber;
    }

    public String getTemplateInitial() {
        return templateInitial;
    }

    public void setTemplateInitial(String templateInitial) {
        this.templateInitial = templateInitial;
    }

    public boolean isProjectTemplate() {
        return isProjectTemplate;
    }

    public void setProjectTemplate(boolean projectTemplate) {
        isProjectTemplate = projectTemplate;
    }

    public String getDetailView() {
        return detailView;
    }

    public void setDetailView(String detailView) {
        this.detailView = detailView;
    }
  
    @Override
    public String toString() {
        return "Template{" +
                "post=" + post +
                ", templateNumber=" + templateNumber +
                ", templateInitial='" + templateInitial + '\'' +
                ", isProjectTemplate=" + isProjectTemplate +
                ", detailView='" + detailView + '\'' +
                '}';
    }
}
