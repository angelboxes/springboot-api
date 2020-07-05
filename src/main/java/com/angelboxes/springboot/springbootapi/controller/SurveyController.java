package com.angelboxes.springboot.springbootapi.controller;

import com.angelboxes.springboot.springbootapi.model.Question;
import com.angelboxes.springboot.springbootapi.model.Survey;
import com.angelboxes.springboot.springbootapi.service.SurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SurveyController {

    private SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }


    @GetMapping("/surveys")
    public List<Survey> getSurveys() {
        return surveyService.retrieveAllSurveys();
    }

    @GetMapping("/surveys/{surveyId}")
    public Survey getSurvey(@PathVariable String surveyId) {
        return surveyService.retrieveSurvey(surveyId);
    }

    @GetMapping("/surveys/{surveyId}/questions")
    public List<Question> getSurveyQuestions(@PathVariable String surveyId) {
        return surveyService.retrieveQuestions(surveyId);
    }

    @GetMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question getSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
        return surveyService.retrieveQuestion(surveyId, questionId);
    }

    @PostMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<?> addQuestions(@PathVariable String surveyId, @RequestBody Question question) {
        Question createdQuestion = surveyService.addQuestion(surveyId, question);

        if (createdQuestion==null){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdQuestion.getId()).toUri();

        return ResponseEntity.created(location).build();    }

}
