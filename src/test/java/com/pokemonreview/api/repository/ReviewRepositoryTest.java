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

    private final static String TITLE = "Review";
    private final static String ANOTHER_TITLE = "Another review";
    private final static String CONTENT = "It is content";
    private final static String ANOTHER_CONTENT = "It is another content";
    private final static int STARS = 5;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void findAll_shouldReturnReviewList() {
        Review review = Review.builder()
                .title(TITLE)
                .content(CONTENT)
                .stars(STARS)
                .build();
        Review review2 = Review.builder()
                .title(ANOTHER_TITLE)
                .content(ANOTHER_CONTENT)
                .stars(STARS)
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
                .title(TITLE)
                .content(CONTENT)
                .stars(STARS)
                .build();
        reviewRepository.save(review);

        Review returnedReview = reviewRepository.findById(review.getId()).orElse(null);

        assertThat(returnedReview).isNotNull();
    }

    @Test
    void save_shouldReturnSavedReview() {
        Review review = Review.builder()
                .title(TITLE)
                .content(CONTENT)
                .stars(STARS)
                .build();

        Review savedReview = reviewRepository.save(review);

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    void save_shouldReturnUpdatedReview() {
        Review review = Review.builder()
                .title(ANOTHER_TITLE)
                .content(ANOTHER_CONTENT)
                .stars(STARS)
                .build();
        reviewRepository.save(review);

        Review updatedReview = null;
        Optional<Review> optionalReview = reviewRepository.findById(review.getId());
        if (optionalReview.isPresent()) {
            optionalReview.get().setTitle(TITLE);
            optionalReview.get().setContent(CONTENT);
            updatedReview = reviewRepository.save(optionalReview.get());
        }

        assertThat(updatedReview).isNotNull();
        assertThat(updatedReview.getTitle()).isNotNull();
        assertThat(updatedReview.getContent()).isNotNull();
    }

    @Test
    void deleteById_shouldDeleteReview() {
        Review review = Review.builder()
                .title(TITLE)
                .content(CONTENT)
                .stars(STARS)
                .build();
        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());
        Optional<Review> optionalReview = reviewRepository.findById(review.getId());

        assertThat(optionalReview).isEmpty();
    }

}
