package com.pokemonreview.api.repository;

import com.pokemonreview.api.model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void findAll_shouldReturnReviewList() {
        Review review = Review.builder()
                .title("Review")
                .content("It is content")
                .stars(5)
                .build();
        Review review2 = Review.builder()
                .title("Review")
                .content("It is content")
                .stars(5)
                .build();
        reviewRepository.save(review);
        reviewRepository.save(review2);

        List<Review> reviewList = reviewRepository.findAll();

        assertThat(reviewList).isNotNull();
        assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    void findById_shouldReturnReview() {
        Review review = Review.builder()
                .title("Review")
                .content("It is content")
                .stars(5)
                .build();
        reviewRepository.save(review);

        Review returnedReview = reviewRepository.findById(review.getId()).orElse(null);

        assertThat(returnedReview).isNotNull();
    }

    @Test
    void save_shouldReturnSavedReview() {
        Review review = Review.builder()
                .title("Review")
                .content("It is content")
                .stars(5)
                .build();

        Review savedReview = reviewRepository.save(review);

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    void save_shouldReturnUpdatedReview() {
        Review review = Review.builder()
                .title("View")
                .content("it is content")
                .stars(5)
                .build();
        reviewRepository.save(review);

        Review updatedReview = null;
        Optional<Review> optionalReview = reviewRepository.findById(review.getId());
        if (optionalReview.isPresent()) {
            optionalReview.get().setTitle("Review");
            optionalReview.get().setContent("It is content");
            updatedReview = reviewRepository.save(optionalReview.get());
        }

        assertThat(updatedReview).isNotNull();
        assertThat(updatedReview.getTitle()).isNotNull();
        assertThat(updatedReview.getContent()).isNotNull();
    }

    @Test
    void deleteById_shouldDeleteReview() {
        Review review = Review.builder()
                .title("Review")
                .content("It is content")
                .stars(5)
                .build();
        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());
        Optional<Review> optionalReview = reviewRepository.findById(review.getId());

        assertThat(optionalReview).isEmpty();
    }

}
