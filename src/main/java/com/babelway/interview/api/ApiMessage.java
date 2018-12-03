package com.babelway.interview.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Copyright Babelway 2017.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ApiMessage {
    @JsonProperty("id")  private UUID id;
    @JsonProperty("company") private String company;
    @JsonProperty("content")   private String content;

    @JsonCreator
    public ApiMessage(@JsonProperty("id") UUID id, @JsonProperty("company") String company, @JsonProperty("content") String content) {
        this.id = id;
        this.company = company;
        this.content = content;
    }

    public ApiMessage() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
