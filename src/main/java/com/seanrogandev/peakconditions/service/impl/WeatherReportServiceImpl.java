package com.seanrogandev.peakconditions.service.impl;

import com.seanrogandev.peakconditions.service.WeatherReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherReportServiceImpl implements WeatherReportService {

    final private String API_BASE_URL = "https://peak-conditions-api.herokuapp.com";

    @Override
    public ResponseEntity<?> getWeatherReport(boolean extendedReportRequested, long peakId) {
        //if an extended report is requested..
        if(extendedReportRequested) {
            try {
                return callApi(API_BASE_URL + "/report/daily/" + peakId);
            } catch (Exception e ) {
                log.error(e.getMessage());
            }
        } else {
            //following code is for when single day report is requested
            try {
                return callApi(API_BASE_URL + "/report/extended/" + peakId);
            } catch (Exception e ) {
                log.error(e.getMessage());
            }
        }
        //if something went wrong and neither code block executed, return an error response
        log.error("Something went wrong calling the external weather API in " + this.getClass().getName() +".getWeatherReport()");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> callApi(String uri) {
        //create a restTemplate to perform http operations
        try {
            RestTemplate template = new RestTemplate();
            log.info("calling external API at endpoint " + uri);
            //get request to the endpoint, saved as an array of objects
            Object[] response = template.getForObject(uri, Object[].class);
            if(response != null) {
                //if there is a response from the api call, return it with an OK 200 response
                return new ResponseEntity<>(Arrays.asList(response), HttpStatus.OK);
                //if its null return an error response
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("something went wrong in " + this.getClass().getName() + ".callApi()");
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
