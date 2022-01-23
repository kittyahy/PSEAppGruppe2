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

public class TemplateDetail {
    private int id;
    private String detail;
    private boolean isProjectTemplate;

    public TemplateDetail(int id, String detail, boolean isProjectTemplate) {
        this.id = id;
        this.detail = detail;
        this.isProjectTemplate = isProjectTemplate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isProjectTemplate() {
        return isProjectTemplate;
    }

    public void setProjectTemplate(boolean projectTemplate) {
        isProjectTemplate = projectTemplate;
    }

    @Override
    public String toString() {
        return "TemplateDetail{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                ", isProjectTemplate=" + isProjectTemplate +
                '}';
    }
}
