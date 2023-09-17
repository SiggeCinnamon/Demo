package com.example.demo.Service;

import com.example.demo.Model.BusLine;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;

import static com.example.demo.Config.Constants.URL_LINEDATA;
import static com.example.demo.Config.Constants.URL_SITEDATA;

@Service
public class TrafficLabsService {
    @Autowired
    WebClient webClient;
    BusLine busLine = new BusLine();

    public static void printMatchingSiteNames(Set<List<String>> matchSiteNames) {
        int i = 1;
        for (List<String> siteNameAndJppn : matchSiteNames) {
            String siteName = siteNameAndJppn.get(0);
            String jppnString = siteNameAndJppn.get(1);
            System.out.println((i++) + ". Site Name: " + siteName + ", Jppn String: " + jppnString);
        }
    }

    public Mono<List<JsonNode>> findAll() {
        return webClient.post()
                .uri(URL_LINEDATA)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .retrieve()
                .bodyToMono(String.class)
                .map(jsonString -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        JsonNode root = objectMapper.readTree(jsonString);
                        List<JsonNode> nodes = root.findParents("Result");
                        int mostStops = findMostFrequentLineNumber(nodes);
                        List<String> tenJPPN = extractAndCount10JPPNForLineNumber(nodes, mostStops);
                        busLine.setMostStops(mostStops);
                        busLine.setJppn(tenJPPN);
                        printResults(mostStops, tenJPPN);
                        //Built myself into a corner here.
                        return nodes;
                    } catch (IOException e) {
                        System.out.println("Error");
                        throw new RuntimeException(e);
                    }
                });
    }

    public Mono<List<JsonNode>> findAll2() {
        return webClient.post()
                .uri(URL_SITEDATA)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .retrieve()
                .bodyToMono(String.class)
                .map(jsonString -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        JsonNode root = objectMapper.readTree(jsonString);
                        List<JsonNode> nodes = root.findParents("Result");
                        //compareSiteNames(nodes, busLine.getJppn());
                        printMatchingSiteNames(compareSiteNames(nodes, busLine.getJppn()));
                        return nodes;
                    } catch (IOException e) {
                        System.out.println("Error");
                        throw new RuntimeException(e);
                    }
                });
    }

    ;

    private Set<List<String>> compareSiteNames(List<JsonNode> nodes, List<String> jppn) {
        Set<List<String>> matchSiteNames = new HashSet<>();
        for (JsonNode parentNode : nodes) {
            JsonNode jNode = parentNode.get("Result");
            if (jNode != null) {
                Iterator<JsonNode> iterator = jNode.elements();
                while (iterator.hasNext()) {
                    JsonNode jObject = iterator.next();
                    if (jObject != null && jObject.has("StopAreaNumber")) {
                        String stopAreaNumber = jObject.get("StopAreaNumber").asText();
                        for (String jppnString : jppn) {
                            if (stopAreaNumber.equals(jppnString)) {
                                String siteName = jObject.get("SiteName").asText();
                                List<String> siteNameAndJppn = new ArrayList<>();
                                siteNameAndJppn.add(siteName);
                                siteNameAndJppn.add(jppnString);
                                matchSiteNames.add(siteNameAndJppn);
                            }
                        }
                    }
                }
            }
        }
        return matchSiteNames;
    }

    private List<String> extractAndCount10JPPNForLineNumber(List<JsonNode> nodes, int mostStops) {
        List<String> result = new ArrayList<>();
        for (JsonNode parentNode : nodes) {
            JsonNode jNode = parentNode.get("Result");
            if (jNode != null) {
                Iterator<JsonNode> iterator = jNode.elements();
                while (iterator.hasNext()) {
                    JsonNode jObject = iterator.next();
                    if (jObject != null && jObject.has("LineNumber")) {
                        String lineNumber = jObject.get("LineNumber").asText();
                        if (lineNumber.equals(String.valueOf(mostStops))) {//??
                            String jppn = jObject.get("JourneyPatternPointNumber").asText();
                            if (!result.contains(jppn)) {
                                result.add(jppn);
                                if (result.size() >= 10) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private int findMostFrequentLineNumber(List<JsonNode> nodes) {
        Map<String, Long> lineNumberCounts = extractAndCountLineNumbers(nodes);
        return lineNumberCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> Integer.parseInt(entry.getKey()))
                .orElse(0);
    }

    private void printResults(int mostStops, List<String> tenJPPN) {
        System.out.println("Most stops for a line: " + mostStops);
        System.out.println("10 JourneyPatternPointNumbers(which should be names) for " + mostStops + ":" + tenJPPN);
    }

    private Map<String, Long> extractAndCountLineNumbers(List<JsonNode> nodes) {
        Map<String, Long> lineNumberCounts = new HashMap<>();
        for (JsonNode parentNode : nodes) {
            JsonNode jNode = parentNode.get("Result");
            if (jNode != null) {
                Iterator<JsonNode> iterator = jNode.elements();
                while (iterator.hasNext()) {
                    JsonNode iObject = iterator.next();
                    if (iObject != null && iObject.has("LineNumber")) {
                        String lineNumber = iObject.get("LineNumber").asText();
                        lineNumberCounts.put(lineNumber, lineNumberCounts.getOrDefault(lineNumber, 0L) + 1);
                    }
                }
            }
        }

        return lineNumberCounts;
    }
}
