package com.pseandroid2.dailydataserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pseandroid2.dailydataserver.postDatabase.PostService;
import com.pseandroid2.dailydataserver.postDatabase.PostsController;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PostsController.class)
public class PostsControllerTest {


    @Autowired
    private PostService service;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper map;

    public PostsControllerTest() {
        service = Mockito.mock(PostService.class);
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }


   /* @Test
    public void addPostTest() throws Exception {
        Pair<String, String> projectTmpl = Pair.of("projectTemplate", "project Detail View");
        Pair<String, String> graphTmp1 = Pair.of("graphTemplate1", "graphtemplate Detail View 1");
        Pair<String, String> graphTmp2 = Pair.of("graphTemplate2", "graphtemplate Detail View 2");
        ArrayList<Pair<String, String>> list = new ArrayList<>();
        list.add(graphTmp1);
        list.add(graphTmp2);

        AddPostParameter params = new AddPostParameter("token tm", "die postpreview", projectTmpl, list);
    }*/

}
