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

import com.pseandroid2.dailydataserver.postDatabase.requestparameters.AddPostParameter;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller to know if the server is available.
 * The client doesn't need to log in for access the controller
 */
@RestController
@CrossOrigin(origins= "http://localhost:8080")
@RequestMapping(path = "/")
public class GreetingController {

    private ServerGreetings serverGreetings;

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
    public AddPostParameter test(){
        Pair<String, String> projectTmpl = Pair.of("projectTemplate", "project Detail View");
        Pair<String, String> graphTmp1 = Pair.of("graphTemplate1", "graphtemplate Detail View 1");
        Pair<String, String> graphTmp2 = Pair.of("graphTemplate2", "graphtemplate Detail View 2");
        ArrayList<Pair<String, String>> list = new ArrayList<>();
        list.add(graphTmp1);
        list.add(graphTmp2);

        AddPostParameter params = new AddPostParameter("token tm", "die postpreview", projectTmpl, list);
        return params;
    }

}
