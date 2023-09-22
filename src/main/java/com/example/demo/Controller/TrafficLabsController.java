package com.example.demo.Controller;


import com.example.demo.Model.TrafficLabs;
import com.example.demo.Service.TrafficLabsService;
import com.example.demo.Util.TrafficLabsDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping
public class TrafficLabsController {
    @Autowired
    TrafficLabsService trafficLabsService;

    @RequestMapping(value = "/JourneyPatternPointOnLine", method = RequestMethod.POST)
    public Mono<List<JsonNode>> findAll() {
        System.out.println("-------Start-------");
        return trafficLabsService.findAll();
    }

    @RequestMapping(value = "/site", method = RequestMethod.POST)
    public Mono<List<JsonNode>> findAll2() {
        System.out.println("-------Start nr 2-------");
        return trafficLabsService.findAll2();
    }

    @RequestMapping(value = "/deserializer", method = RequestMethod.POST)
    public TrafficLabs findAll3(Mono<List<JsonNode>> monoList) {
        System.out.println("-------Start nr 3-------");

        TrafficLabs trafficLabs = new TrafficLabs();
        TrafficLabsDeserializer trafficLabsDeserializer = new TrafficLabsDeserializer();
        trafficLabsDeserializer.mapper(monoList);
        return trafficLabs;
    }
}

    


