package com.catalog.moviecatalogservice.controller;

import com.catalog.moviecatalogservice.model.CatalogItem;
import com.catalog.moviecatalogservice.service.CatalogItemService;
import com.catalog.moviecatalogservice.service.RatingService;
import com.info.movieinfoservice.data.Movie;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rating.movieratingservice.model.Rating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("catalog")
@Slf4j
public class MovieCatalogController {
    
   @Autowired
   private RatingService ratingService;

   @Autowired
   private CatalogItemService catalogItemService;
    
    @RequestMapping("/{userId}")
//    @HystrixCommand(fallbackMethod = "getFallbackCatalogs")
    public List<CatalogItem> getCatalogs(@PathVariable("userId") String userId) {

      List<Map> ratings = ratingService.getRating();
       return ratings.stream().
               map(this::getRating).
               map(catalogItemService::getCatalogItem).collect(Collectors.toList());
//        return Arrays.asList(new CatalogItem("STAR WARS", "Star wars VIII", 8));
    }

    private Rating getRating(Map map) {
        int id = (int)map.get("id");
        String movieName = (String) map.get("movieName");
        return new Rating(id, movieName);
    }
    
    public List<CatalogItem> getFallbackCatalogs(String userId) {
        log.info("falling to getFallbackCatalogs");
        return Arrays.asList(new CatalogItem(userId,"dummy", -1));
    } 
    

}
