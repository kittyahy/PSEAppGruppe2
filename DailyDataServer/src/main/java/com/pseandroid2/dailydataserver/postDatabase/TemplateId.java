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

import java.io.Serializable;
import java.util.Objects;

public class TemplateId implements Serializable {
    private int post;
    private int templateNumber;

    public TemplateId(int postID, int templateNumber) {
        this.post = postID;
        this.templateNumber = templateNumber;
    }

    public TemplateId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateId that = (TemplateId) o;
        return post == that.post && templateNumber == that.templateNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, templateNumber);
    }
}
