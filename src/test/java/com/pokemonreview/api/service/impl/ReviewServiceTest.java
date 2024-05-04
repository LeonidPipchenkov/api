package com.pokemonreview.api.service.impl;

import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.model.Pokemon;
import com.pokemonreview.api.model.Review;
import com.pokemonreview.api.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    private final static int POKEMON_ID = 1;
    private final static int REVIEW_ID = 1;

    @Mock
    private ReviewRepository reviewRepository;
    @Spy
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void getReviewsByPokemonId_shouldReturnReviewDtos() {
        Review review = mock(Review.class);
        doReturn(List.of(review)).when(reviewRepository).findByPokemonId(POKEMON_ID);

        List<ReviewDto> reviewDtos = reviewService.getReviewsByPokemonId(POKEMON_ID);

        assertThat(reviewDtos).isNotNull();
    }

    @Test
    void getReviewById_shouldReturnReviewDto() {
        Pokemon pokemon = mock(Pokemon.class);
        doReturn(POKEMON_ID).when(pokemon).getId();
        doReturn(pokemon).when(reviewService).fetchPokemon(POKEMON_ID);
        Review review = mock(Review.class);
        doReturn(pokemon).when(review).getPokemon();
        doReturn(Optional.of(review)).when(reviewRepository).findById(REVIEW_ID);

        ReviewDto returnedReviewDto = reviewService.getReviewById(POKEMON_ID, REVIEW_ID);

        assertThat(returnedReviewDto).isNotNull();
    }

    @Test
    void createReview_shouldReturnCreatedReviewDto() {
        ReviewDto reviewDto = mock(ReviewDto.class);
        Pokemon pokemon = mock(Pokemon.class);
        doReturn(pokemon).when(reviewService).fetchPokemon(POKEMON_ID);
        Review createdReview = mock(Review.class);
        doReturn(createdReview).when(reviewRepository).save(any(Review.class));

        ReviewDto createdReviewDto = reviewService.createReview(POKEMON_ID, reviewDto);

        assertThat(createdReviewDto).isNotNull();
    }

    @Test
    void updateReview_shouldReturnUpdatedReviewDto() {
        ReviewDto reviewDto = mock(ReviewDto.class);
        Pokemon pokemon = mock(Pokemon.class);
        doReturn(POKEMON_ID).when(pokemon).getId();
        doReturn(pokemon).when(reviewService).fetchPokemon(POKEMON_ID);
        Review review = mock(Review.class);
        doReturn(pokemon).when(review).getPokemon();
        doReturn(Optional.of(review)).when(reviewRepository).findById(REVIEW_ID);
        doReturn(review).when(reviewRepository).save(any(Review.class));

        ReviewDto updatedReviewDto = reviewService.updateReview(POKEMON_ID, REVIEW_ID, reviewDto);

        assertThat(updatedReviewDto).isNotNull();
    }

    @Test
    void deleteReview_shouldCallDelete() {
        Pokemon pokemon = mock(Pokemon.class);
        doReturn(POKEMON_ID).when(pokemon).getId();
        doReturn(pokemon).when(reviewService).fetchPokemon(POKEMON_ID);
        Review review = mock(Review.class);
        doReturn(pokemon).when(review).getPokemon();
        doReturn(Optional.of(review)).when(reviewRepository).findById(REVIEW_ID);

        reviewService.deleteReview(POKEMON_ID, REVIEW_ID);

        verify(reviewRepository).delete(review);
    }

}
