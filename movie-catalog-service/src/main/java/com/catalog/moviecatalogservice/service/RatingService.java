package com.catalog.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RatingService {

    @Autowired
    private RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod = "getFallbackRating",
                    commandProperties = {
                            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
                            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
                            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000"),
                            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "180000") } )
    public List getRating() {
        return restTemplate.getForObject("http://movie-rating-service/rating/all", List.class);
    }

    private List getFallbackRating() {
        log.info("RatingService getFallbackRating");
        Map map = new HashMap();
        map.put("id", -1);
        map.put("movieName", "DUMMU DUMMY");
        return Arrays.asList(map);
    }
}
