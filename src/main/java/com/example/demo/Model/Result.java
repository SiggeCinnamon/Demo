package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    @JsonIgnoreProperties
    private int LastModifiedUtcDateTime;
    @JsonIgnoreProperties
    private int ExistsFromDate;
    @JsonProperty("LineNumber")
    private int lineNumber;
    @JsonProperty("DirectionCode")
    private int directionCode;
    @JsonProperty("JourneyPatternPointNumber")
    private int journeyPatternPointNumber;


}
