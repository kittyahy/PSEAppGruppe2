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
import java.util.List;

@RestController
@RequestMapping(path = "/test")
public class TestsController {

    @GetMapping("/token")
    public String test(@RequestHeader String token) {

        return "token";
    }

    @GetMapping("/username")
    public String name(@RequestHeader String token, @RequestAttribute String user) {
        return user;
    }

    @GetMapping("/u")
    public AddPostParameter para() {
        Pair projectTemplate = Pair.of("projectTemplate", "project Preview");
        Pair graphTemplate1 = Pair.of("graph Template 1", "graph Preview 1");
        Pair graphTemplate2 = Pair.of("graph Template 2", "graph Preview 2");

        List l = new ArrayList();
        l.add(graphTemplate1);
        l.add(graphTemplate2);

        return new AddPostParameter("Postpreview", projectTemplate, l);
    }

}