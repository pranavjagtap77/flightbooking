package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import java.util.Collections;
import java.util.Map;

@Controller
public class FlightController {

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/search")
    public String searchFlights(@RequestParam String origin,
                                @RequestParam String destination,
                                @RequestParam String cabin,
                                Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://cardgpt.in/apitest";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonPayload = String.format("{\"origin\":\"%s\",\"destination\":\"%s\"," +
                        "\"partnerPrograms\":[\"Air Canada\",\"United Airlines\",\"KLM\",\"Qantas\"," +
                        "\"American Airlines\",\"Etihad Airways\",\"Alaska Airlines\",\"Qatar Airways\",\"LifeMiles\"]," +
                        "\"stops\":2,\"departureTimeFrom\":\"2024-07-09T00:00:00Z\",\"departureTimeTo\":\"2024-10-07T00:00:00Z\"," +
                        "\"isOldData\":false,\"limit\":302,\"offset\":0,\"cabinSelection\":[\"%s\"],\"date\":\"2024-07-09T12:00:17.796Z\"}",
                origin, destination, cabin);

        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        model.addAttribute("data", response.getBody().get("data"));

        return "index";
    }
}
