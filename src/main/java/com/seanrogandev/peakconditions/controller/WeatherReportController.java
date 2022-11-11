package com.seanrogandev.peakconditions.controller;

import com.seanrogandev.peakconditions.service.WeatherReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j

public class WeatherReportController {
    //todo after security is implemented in the API
    // login to api
    WeatherReportService weatherReportService;

    //get one day report
@GetMapping("/getDailyReport/{peakId}")
public ResponseEntity<?> getOneDayWeatherReport(@PathVariable Long peakId) {
    try {
        return weatherReportService.getWeatherReport(false, peakId);
    } catch (Exception e ) {
        log.error(e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//get six day report

@GetMapping("/getExtendedReport/{peakId}")
    public ResponseEntity<?> getExtendedWeatherReport(@PathVariable Long peakId) {
    try {
        return weatherReportService.getWeatherReport(true, peakId);
    } catch (Exception e ) {
        log.error(e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
