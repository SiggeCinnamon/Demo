package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BusLine implements Serializable {
    List<String> jppn;
    int mostStops;
    @JsonProperty
    String SiteName;
}
