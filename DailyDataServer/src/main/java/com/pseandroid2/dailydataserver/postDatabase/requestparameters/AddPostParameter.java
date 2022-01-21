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
package com.pseandroid2.dailydataserver.postDatabase.requestparameters;

import com.pseandroid2.dailydataserver.RequestParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * #TODO Testen, JavaDoc
 */
public class AddPostParameter extends RequestParameter {

    private String postPreview;
    private String projectTemplate;
    private List<String> graphTemplates;

    /**
     * @param token           the token, to verify the user, provided by the client
     * @param postPreview     the postPreview for the post, provided by the client
     * @param projectTemplate the projectTemplate, provided by the client
     * @param graphTemplates  all graphTemplates, provided by the client
     */
    public AddPostParameter(String token, String postPreview, String projectTemplate, List<String> graphTemplates) {
        super(token);
        this.postPreview = postPreview;
        this.projectTemplate = projectTemplate;
        this.graphTemplates.addAll(graphTemplates);
    }


    public String getPostPreview() {
        return postPreview;
    }

    public void setPostPreview(String postPreview) {
        this.postPreview = postPreview;
    }

    public String getProjectTemplate() {
        return projectTemplate;
    }

    public void setProjectTemplate(String projectTemplate) {
        this.projectTemplate = projectTemplate;
    }

    public List<String> getGraphTemplates() {
        List<String> list = new ArrayList<>();
        list.addAll(graphTemplates);
        return list;
    }

    public void setGraphTemplates(List<String> graphTemplates) {
        this.graphTemplates.addAll(graphTemplates);
    }

    @Override
    public String toString() {
        return "AddPostParameter{" +
                "postPreview='" + postPreview + '\'' +
                ", projectTemplate='" + projectTemplate + '\'' +
                ", graphTemplates=" + graphTemplates.toString() +
                "} " + super.toString();
    }
}
