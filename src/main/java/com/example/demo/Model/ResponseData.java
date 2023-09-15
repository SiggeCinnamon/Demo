package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseData implements Serializable {
    @JsonIgnoreProperties
    private String Version;
    @JsonIgnoreProperties
    private String Type;
    @JsonProperty("Result")
    private List<Result> result;


}
