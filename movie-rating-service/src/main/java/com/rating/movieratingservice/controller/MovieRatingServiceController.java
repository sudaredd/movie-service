package com.rating.movieratingservice.controller;

import com.rating.movieratingservice.model.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rating")
public class MovieRatingServiceController {
    
    @RequestMapping("/{id}")
    public Rating getRating(@PathVariable("id") int id) {
        return new Rating(id, "SWAE");
    } 
    @RequestMapping("/all")
    public List<Rating> getRatings() {
        return Arrays.asList(new Rating(26,"Star warrr"), new Rating(100,"Jungle book"));
    }
}
