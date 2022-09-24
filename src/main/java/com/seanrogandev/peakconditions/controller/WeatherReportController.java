package com.seanrogandev.peakconditions.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j

public class WeatherReportController {
    final private String API_BASE_URL = "https://peak-conditions-api.herokuapp.com";
    //todo after security is implemented in the API
    // login to api

    //get one day report
@GetMapping("/getDailyReport/{peakId}")
public ResponseEntity<List> getOneDayWeatherReport(@PathVariable Long peakId) {
    try {

        String uri = API_BASE_URL + "/report/daily/" + peakId;
        RestTemplate template = new RestTemplate();
        Object[] response = template.getForObject(uri, Object[].class);
        return new ResponseEntity(Arrays.asList(response), HttpStatus.OK);
    } catch (Exception e ) {
        log.error(e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//get six day report
    //get mountain object id


}
