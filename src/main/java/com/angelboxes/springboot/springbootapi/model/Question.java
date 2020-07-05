package com.angelboxes.springboot.springbootapi.model;

import java.util.List;

public class Question {

    private String id;
    private String description;
    private String correctAnswers;
    private List<String> options;


    public Question(){

    }

    public Question(String id, String description, String correctAnswers, List<String> options) {
        this.id = id;
        this.description = description;
        this.correctAnswers = correctAnswers;
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", correctAnswers='" + correctAnswers + '\'' +
                ", options=" + options +
                '}';
    }
}
