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
package com.pseandroid2.dailydataserver;

import com.pseandroid2.dailydataserver.postDatabase.Request.AddPostParameter;
import com.pseandroid2.dailydataserver.postDatabase.Request.PostPreviewWrapper;
import com.pseandroid2.dailydataserver.postDatabase.Request.TemplateDetailWrapper;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller to know if the server is available.
 * The client doesn't need to log in for access the controller
 */
@RestController
@RequestMapping(path = "/")
public class GreetingController {

    private final ServerGreetings serverGreetings;

    /**
     * Constructor to create the GreetingController
     */
    public GreetingController() {
        this.serverGreetings = new ServerGreetings();
    }

    /**
     * Returns a String with length, longer than 0, to make sure the server is available.
     *
     * @return greeting to signalise, the server is reachable.
     */
    @GetMapping("/greet")
    public String greets() {
        return serverGreetings.greeting();
    }

    @GetMapping("/test")
    public AddPostParameter t(){
        PostPreviewWrapper post = new PostPreviewWrapper(new byte[]{1,2,3},"postPreview");

        Pair<String, TemplateDetailWrapper> projectTemplate = Pair.of("projectTemplate",new TemplateDetailWrapper(new byte[]{1,2},"projectPreview"));
        List<Pair<String, TemplateDetailWrapper>> graphtemplates = new ArrayList<>();
        Pair<String,TemplateDetailWrapper> template = Pair.of("graph template ",new TemplateDetailWrapper(new byte[]{2,3},"graph preview"));
        graphtemplates.add(template);


        return new AddPostParameter(post,projectTemplate,graphtemplates);

    }

}
