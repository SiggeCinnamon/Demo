package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class TrafficLabs implements Serializable {
    @JsonProperty("StatusCode")
    private int statusCode;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("ExecutionTime")
    private int executionTime;
    @JsonProperty("ResponseData")
    private ResponseData responseData;


}
