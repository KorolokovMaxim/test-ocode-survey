package com.ocodetest.survey.payload.request;

import javax.validation.constraints.NotBlank;

public class SurveyRequest {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
