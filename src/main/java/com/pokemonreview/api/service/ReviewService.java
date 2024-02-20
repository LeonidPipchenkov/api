package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    List<ReviewDto> getReviewsByPokemonId(int pokemonId);
    ReviewDto getReviewById(int pokemonId, int reviewId);
    ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
    ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto);
    void deleteReview(int pokemonId, int reviewId);

}
