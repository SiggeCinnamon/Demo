package com.example.demo.Model;

import lombok.Data;

import java.util.List;

@Data
public class BusLine {

    List<String> jppn;
    int mostStops;
    String SiteName;
}
