package com.catalog.moviecatalogservice.service;

import com.catalog.moviecatalogservice.model.CatalogItem;
import com.info.movieinfoservice.data.Movie;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rating.movieratingservice.model.Rating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class CatalogItemService {


    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = webClientBuilder.build()
                .get()
//                .uri("http://localhost:8081/movieInfo/"+rating.getId())
                .uri("http://movie-info-service/movieInfo/"+rating.getId())
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
//        Movie movie =  restTemplate.getForObject("http://localhost:8081/movieInfo/"+rating.getId(), Movie.class);
        return new CatalogItem(movie.getName(), "move desc", rating.getId());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating) {
        log.info("falling to getFallbackCatalogItem");
        return new CatalogItem("dummy","dummy", rating.getId());
    }
}


