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

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which specifies all Parameters, which are recommended for the body of addPost.
 */
public class AddPostParameter {

    private String postPreview;
    private Pair<String, String> projectTemplate;
    private List<Pair<String, String>> graphTemplates;

    /**
     * The constructor to create the parameters for addPost.
     *
     * @param postPreview     the postPreview for the post.
     * @param projectTemplate the projectTemplate. The first entry is the Template, the second is the detailView of
     *                        the template.
     * @param graphTemplates  all graphTemplates.  The first entry  of the Pair is the Template, the second is the
     *                        detailView of the template.
     */
    public AddPostParameter(String postPreview, Pair<String, String> projectTemplate,
                            List<Pair<String, String>> graphTemplates) {
        this.postPreview = postPreview;
        this.projectTemplate = projectTemplate;
        this.graphTemplates = new ArrayList<>();
        this.graphTemplates.addAll(graphTemplates);
    }

    /**
     * Getter for postPreview.
     * <p>
     * There are no need for setters, because the class is only for transfer the Data from the
     * client (who sets them) to the server (who gets them)
     *
     * @return the postPreview for the post.
     */
    public String getPostPreview() {
        return postPreview;
    }

    /**
     * Getter for projectTemplate.
     * <p>
     * There are no need for setters, because the class is only for transfer the Data from the
     * client (who sets them) to the server (who gets them)
     *
     * @return the projectTemplate. The first entry is the Template, the second is the detailView of
     * the template.
     */
    public Pair<String, String> getProjectTemplate() {
        return projectTemplate;
    }


    /**
     * Getter for graph templates, which returns a copy of  graphTemplates
     * <p>
     * There are no need for setters, because the class is only for transfer the Data from the
     * client (who sets them) to the server (who gets them)
     *
     * @return all graphTemplates.  The first entry  of the Pair is the Template, the second is the
     * *                        detailView of the template.
     */
    public List<Pair<String, String>> getGraphTemplates() {
        List<Pair<String, String>> list = new ArrayList<>();
        list.addAll(graphTemplates);
        return list;
    }
    

    @Override
    public String toString() {
        return "AddPostParameter{" +
                "postPreview='" + postPreview + '\'' +
                ", projectTemplate='" + projectTemplate + '\'' +
                ", graphTemplates=" + graphTemplates.toString() +
                "} ";
    }
}
