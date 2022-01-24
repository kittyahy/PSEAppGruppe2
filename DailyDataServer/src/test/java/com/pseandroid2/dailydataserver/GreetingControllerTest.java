package com.pseandroid2.dailydataserver;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = GreetingController.class)
public class GreetingControllerTest {

  /*  @Autowired
    private MockMvc mock;

    @MockBean
    private ServerGreetings serverGreetings;

    @Test
    public void testGreets() throws Exception {
        Mockito.when(serverGreetings.greeting()).thenReturn("Hello");

        mock.perform(get("/")).andExpect(jsonPath("", Matchers.equalTo("Hello")));
    }*/
}
