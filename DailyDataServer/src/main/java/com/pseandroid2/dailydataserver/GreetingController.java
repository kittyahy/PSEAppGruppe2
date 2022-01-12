package com.pseandroid2.dailydataserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to know if the server is available.
 * The client doesn't need to log in for access the controller
 *
 * Has a succeeded Unit test
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
     * @return greeting
     */
    @GetMapping("/")
    public String greets() {
        return serverGreetings.greeting();
    }

}
