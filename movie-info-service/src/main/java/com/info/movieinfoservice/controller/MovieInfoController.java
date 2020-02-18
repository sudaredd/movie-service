package com.info.movieinfoservice.controller;

import com.info.movieinfoservice.data.Movie;
import com.info.movieinfoservice.data.MovieSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movieInfo")
public class MovieInfoController {
    
    @Value("${moviedb.api.key}")
    private  String apiKey;
    
    RestTemplate restTemplate = new RestTemplate();
    @RequestMapping("/{id}")
    public Movie getMovie(@PathVariable("id") String id) {
        String url = "https://api.themoviedb.org/3/movie/"+id + "?api_key="+apiKey;
       MovieSummary summary = restTemplate.getForObject(url, MovieSummary.class);
        return new Movie(id, summary.getTitle(), summary.getOverview());
    }
    
}
