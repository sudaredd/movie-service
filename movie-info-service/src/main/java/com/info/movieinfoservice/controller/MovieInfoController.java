package com.info.movieinfoservice.controller;

import com.info.movieinfoservice.data.Movie;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movieInfo")
public class MovieInfoController {
    
    @RequestMapping("/{id}")
    public Movie getMovie(@PathVariable("id") String id) {
        return new Movie(id, "Star Wars");
    }
    
}
