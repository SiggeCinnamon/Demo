package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonRootName("ResponseData")
public class ResponseData implements Serializable {
    @JsonProperty("Version")
    private String Version;
    @JsonProperty("Type")
    private String Type;
    @JsonProperty("Result")
    private List<Result> result;


}
