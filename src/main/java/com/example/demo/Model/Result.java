package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonRootName("Result")
public class Result implements Serializable {
    @JsonProperty("LastModifiedUtcDateTime")
    private String LastModifiedUtcDateTime;
    @JsonProperty("ExistsFromDate")
    private String ExistsFromDate;
    @JsonProperty("LineNumber")
    private int lineNumber;
    @JsonProperty("DirectionCode")
    private int directionCode;
    @JsonProperty("JourneyPatternPointNumber")
    private int journeyPatternPointNumber;


}
