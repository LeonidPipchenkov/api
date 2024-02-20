package com.pokemonreview.api.controller;

import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/pokemon/{pokemonId}/review")
    public ResponseEntity<List<ReviewDto>> getReviewsByPokemonId(@PathVariable int pokemonId) {
        return new ResponseEntity<>(reviewService.getReviewsByPokemonId(pokemonId), HttpStatus.OK);
    }

    @GetMapping("/pokemon/{pokemonId}/review/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable int pokemonId, @PathVariable int reviewId) {
        ReviewDto response = reviewService.getReviewById(pokemonId, reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/pokemon/{pokemonId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable int pokemonId, @RequestBody ReviewDto reviewDto) {
        ReviewDto response = reviewService.createReview(pokemonId, reviewDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/pokemon/{pokemonId}/review/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable int pokemonId, @PathVariable int reviewId, @RequestBody ReviewDto reviewDto) {
        ReviewDto response = reviewService.updateReview(pokemonId, reviewId, reviewDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/pokemon/{pokemonId}/review/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable int pokemonId, @PathVariable int reviewId) {
        reviewService.deleteReview(pokemonId, reviewId);
        return new ResponseEntity<>("Review deleted", HttpStatus.OK);
    }

}
