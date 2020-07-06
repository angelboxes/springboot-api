package com.angelboxes.springboot.springbootapi.controller;

import com.angelboxes.springboot.springbootapi.model.Question;
import com.angelboxes.springboot.springbootapi.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebMvcTest(SurveyController.class)
@WithMockUser(username = "user1", password = "secret1")
public class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SurveyService surveyService;

    @BeforeEach
    public void beforeEach() {


    }

    @Test
    public void retrieveDetailsForQuestion() throws Exception {
        Question mockQuestion = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));
        when(surveyService.retrieveQuestion(anyString(), anyString())).thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/surveys/Survey1/questions/Question1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{id:Question1}";
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void createSurveyQuestion() throws Exception {
        Question mockQuestion = new Question("Questionx",
                "My favorite number", "4", Arrays.asList(
                "4", "1", "2", "3"));
        when(surveyService.addQuestion(anyString(), any(Question.class))).thenReturn(mockQuestion);
        String question = "{\n" +
                "    \"id\": \"Questionxx\",\n" +
                "    \"description\": \"Where do you live?\",\n" +
                "    \"correctAnswers\": \"My house\",\n" +
                "    \"options\": [\n" +
                "        \"My house\",\n" +
                "        \"Your house\",\n" +
                "        \"His house\",\n" +
                "        \"In the trees\"\n" +
                "    ]\n" +
                "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/surveys/Survey1/questions")
                .content(question)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertTrue(response.getHeaders(HttpHeaders.LOCATION).get(0).contains("surveys/Survey1/questions/Questionx"));

    }


}
