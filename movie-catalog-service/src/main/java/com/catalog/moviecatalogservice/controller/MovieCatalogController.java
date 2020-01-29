package com.catalog.moviecatalogservice.controller;

import com.catalog.moviecatalogservice.model.CatalogItem;
import com.info.movieinfoservice.data.Movie;
import com.rating.movieratingservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("catalog")
public class MovieCatalogController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalogs(@PathVariable("userId") String userId) {
        
        List<Rating> ratings = Arrays.asList(new Rating(23,"Star warrr"), new Rating(45,"Jungle book"));
        
       return ratings.stream().map(this::getCatalogItem).collect(Collectors.toList());
//        return Arrays.asList(new CatalogItem("STAR WARS", "Star wars VIII", 8));
    }
    
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie =  restTemplate.getForObject("http://localhost:8081/movieInfo/"+rating.getId(), Movie.class);
        return new CatalogItem(movie.getName(), "move desc", rating.getId());
    }
}
