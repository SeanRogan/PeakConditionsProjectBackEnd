package com.seanrogandev.peakconditions.service;

import org.springframework.http.ResponseEntity;

public interface WeatherReportService {

    ResponseEntity<?> getWeatherReport(boolean extendedReportRequested, long peakId);

}
