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
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which specifies all Parameters, which are recommended for the body of addPost.
 */
@Getter
@Setter
public class AddPostParameter {

    private PostPreviewWrapper postPreview;
    private Pair<String, TemplateDetailWrapper> projectTemplate;
    private List<Pair<String, TemplateDetailWrapper>> graphTemplates;

    /**
     * The constructor to create the parameters for addPost.
     *
     * @param postPreview     the postPreview for the post.
     * @param projectTemplate the projectTemplate. The first entry is the Template, the second is the detailView of
     *                        the template.
     * @param graphTemplates  all graphTemplates.  The first entry  of the Pair is the Template, the second is the
     *                        detailView of the template.
     */
    public AddPostParameter(PostPreviewWrapper postPreview, Pair<String, TemplateDetailWrapper> projectTemplate,
                            List<Pair<String, TemplateDetailWrapper>> graphTemplates) {
        this.postPreview = postPreview;
        this.projectTemplate = projectTemplate;
        this.graphTemplates = new ArrayList<>();
        this.graphTemplates.addAll(graphTemplates);
    }


    @Override
    public String toString() {
        return "AddPostParameter{" +
                "postPreview='" + postPreview + '\'' +
                ", projectTemplate=" + projectTemplate +
                ", graphTemplates=" + graphTemplates +
                '}';
    }
}
