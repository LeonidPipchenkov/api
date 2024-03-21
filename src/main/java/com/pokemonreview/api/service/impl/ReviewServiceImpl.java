package com.pokemonreview.api.service.impl;

import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.exception.PokemonNotFoundException;
import com.pokemonreview.api.exception.ReviewNotFoundException;
import com.pokemonreview.api.model.Pokemon;
import com.pokemonreview.api.model.Review;
import com.pokemonreview.api.repository.ReviewRepository;
import com.pokemonreview.api.service.PokemonService;
import com.pokemonreview.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PokemonService pokemonService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonService pokemonService) {
        this.reviewRepository = reviewRepository;
        this.pokemonService = pokemonService;
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int pokemonId) {
        List<Review> reviews = reviewRepository.findByPokemonId(pokemonId);
        return reviews.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int pokemonId, int reviewId) {
        Pokemon pokemon = fetchPokemon(pokemonId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associated pokemon could not be found"));
        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }
        return mapToDto(review);
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);
        Pokemon pokemon = fetchPokemon(pokemonId);
        review.setPokemon(pokemon);
        Review createdReview = reviewRepository.save(review);
        return mapToDto(createdReview);
    }

    @Override
    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
        Pokemon pokemon = fetchPokemon(pokemonId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associated pokemon could not be found"));
        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {
        Pokemon pokemon = fetchPokemon(pokemonId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with associated pokemon could not be found"));
        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }
        reviewRepository.delete(review);
    }

    private Pokemon fetchPokemon(int pokemonId) {
        return pokemonService.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review could not be found"));
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }

}
