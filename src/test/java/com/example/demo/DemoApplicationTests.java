package com.example.demo;

import com.example.demo.Model.ResponseData;
import com.example.demo.Model.Result;
import com.example.demo.Model.TrafficLabs;
import com.example.demo.util.FileUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {

    }

    // JsonNode jsonNode = objectMapper.readTree(json);
    @Test
    public void testTrafficLabsMapping() throws IOException {
        String trafficLabsJson = FileUtil.readFromFileToString("/1.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(trafficLabsJson);
        TrafficLabs trafficLabs = objectMapper.treeToValue(jsonNode, TrafficLabs.class);
        System.out.println("StatusCode: " + trafficLabs.getStatusCode());
        System.out.println("Message: " + trafficLabs.getMessage());
        assertEquals(0, trafficLabs.getStatusCode());
        assertEquals(null, trafficLabs.getMessage());
        assertNotNull(trafficLabs.getResponseData());
        testResponseMapping(trafficLabs.getResponseData());
    }

    private void testResponseMapping(ResponseData responseData) {
        assertNotNull(responseData.getResult());
        assertTrue(responseData.getResult().size() > 0);
        System.out.println("Version: " + responseData.getVersion());
        System.out.println("Type: " + responseData.getType());
        for (Result result : responseData.getResult()) {
            assertNotNull("JourneyPatternPointNumber", String.valueOf(result.getJourneyPatternPointNumber()));
            assertNotNull("LineNumber", String.valueOf(result.getLineNumber()));
        }
        testResultMapping(responseData.getResult());
    }

    private void testResultMapping(List<Result> results) {
        for (int i = 0; i < 5; i++) {
            Result result = results.get(i);
            System.out.println("Result " + i + ":");
            System.out.println("LineNumber: " + result.getLineNumber());
            System.out.println("JourneyPatternPointNumber: " + result.getJourneyPatternPointNumber());
            assertNotNull(result.getLineNumber());
            assertNotNull(result.getJourneyPatternPointNumber());
        }
    }

    private void testFindMostFrequentLineNumber() {
        //TODO
    }

    private void testCompareSiteNames() {
        //TODO
    }

    private void testExtractAndCount10JPPNForLineNumber() {
        //TODO
    }

    private void testExtractAndCountLineNumbers() {
        //TODO
    }
}


