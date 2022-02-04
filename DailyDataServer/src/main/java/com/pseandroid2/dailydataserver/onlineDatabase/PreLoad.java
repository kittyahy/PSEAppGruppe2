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
package com.pseandroid2.dailydataserver.onlineDatabase;

import com.pseandroid2.dailydataserver.postDatabase.PostService;
import com.pseandroid2.dailydataserver.postDatabase.Request.PostPreviewWrapper;
import com.pseandroid2.dailydataserver.postDatabase.Request.TemplateDetailWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Preloads data to the database.
 */
@Configuration
public class PreLoad {
    private static final Logger log = LoggerFactory.getLogger(PreLoad.class);

    /**
     * It initialises the table with a post and logs it.
     *
     * @return the command line runner to initializes the database.
     */
    @Bean
    CommandLineRunner initDatabase(PostService postService) {

        PostPreviewWrapper postPreview = new PostPreviewWrapper(new Byte[]{1, 2, 3}, "Title of post");
        TemplateDetailWrapper projectTemplateDetail = new TemplateDetailWrapper(new Byte[]{2, 3, 4},
                "projectTemplate title");
        TemplateDetailWrapper graphTemplateDetail = new TemplateDetailWrapper(new Byte[]{3, 4, 5},
                "graph template title");
        Pair<String, TemplateDetailWrapper> projectTemplate = Pair.of("the Template", projectTemplateDetail);
        Pair<String, TemplateDetailWrapper> graphTemplate = Pair.of("the Graph template", graphTemplateDetail);
        List<Pair<String, TemplateDetailWrapper>> allGraphTemplates = new ArrayList<>();
        allGraphTemplates.add(graphTemplate);


        return args -> {
            log.info("New Post: " + postService.addPost(postPreview, projectTemplate, allGraphTemplates, "admin"));

        };
    }
}
