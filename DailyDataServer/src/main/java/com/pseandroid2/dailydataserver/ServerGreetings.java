package com.pseandroid2.dailydataserver;

import org.springframework.stereotype.Component;

/**
 * Logic for greeting
 */
@Component
public class ServerGreetings {

    /**
     * returns a greeting String, with length > 0.
     *
     * @return the recommended String
     */
    public String greeting(){
        return "Hello";
    }
}

