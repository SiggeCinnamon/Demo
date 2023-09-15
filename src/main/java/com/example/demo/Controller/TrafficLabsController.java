package com.example.demo.Controller;


import com.example.demo.Service.TrafficLabsService;
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

    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    public Mono<List<JsonNode>> findAll() {
        System.out.println("-------Start-------");
        return trafficLabsService.findAll2();
    }
}

    


