package com.angelboxes.springboot.springbootapi.controller;

import com.angelboxes.springboot.springbootapi.SpringbootapiApplication;
import com.angelboxes.springboot.springbootapi.model.Question;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = SpringbootapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveryControllerTestIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    public String createHeadersWithUserAndPassword(String user, String password) {
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
        String headerValue = "Basic " + new String(encodedAuth);
        return headerValue;

    }

    @BeforeEach
    public void beforeEach() {
        headers.add("Authorization", createHeadersWithUserAndPassword("user1", "secret1"));

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testJsonAssert() throws JSONException {
        String jsonExpected = "{id:1,name:Ringo, role:Admin}";
        JSONAssert.assertEquals("{id:1, role:Admin}", jsonExpected, false);

    }

    @Test
    public void testRetrieveSurveyQuestion() throws JSONException {
        String url = createUrl("/surveys/Survey1/questions/Question1");
        String output = restTemplate.getForObject(url, String.class);
        System.out.println("Response: " + output);


        HttpEntity entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        JSONAssert.assertEquals("{id:Question1}", responseEntity.getBody(), false);
        System.out.println("Response: " + responseEntity.getBody());

    }


    @Test
    public void addQuestionTest() throws JSONException {
        String url = createUrl("/surveys/Survey1/questions");

        Question question1 = new Question("QuestionXXX",
                "Who are you?", "Somebody", Arrays.asList(
                "Nobody", "Anybody", "Somebody"));

        HttpEntity entity = new HttpEntity<Question>(question1, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String actual = responseHeaders.get(HttpHeaders.LOCATION).get(0);
        assertTrue(actual.contains("/surveys/Survey1/questions"));


    }

    private String createUrl(String retrieveAllQuestions) {
        return "http://localhost:" + port + retrieveAllQuestions;
    }

}
