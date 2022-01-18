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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to know if the server is available.
 * The client doesn't need to log in for access the controller
 *
 */
@RestController
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

}
