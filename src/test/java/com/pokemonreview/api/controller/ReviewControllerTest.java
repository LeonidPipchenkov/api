package com.pokemonreview.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.service.ReviewService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    private final static int POKEMON_ID = 1;
    private final static int REVIEW_ID = 1;
    private final static String TITLE = "Review";
    private final static String CONTENT = "It is content";
    private final static int STARS = 5;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @Test
    void getReviewsByPokemonId_shouldReturnOk() throws Exception {
        ReviewDto reviewDto = ReviewDto.builder()
                .id(REVIEW_ID)
                .title(TITLE)
                .content(CONTENT)
                .stars(STARS)
                .build();
        willReturn(List.of(reviewDto)).given(reviewService).getReviewsByPokemonId(POKEMON_ID);

        ResultActions response = mockMvc.perform(get("/api/pokemon/" + POKEMON_ID + "/review")
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(List.of(reviewDto).size())));
    }

    @Test
    void getReviewById_shouldReturnOk() throws Exception {
        ReviewDto reviewDto = ReviewDto.builder()
                .id(REVIEW_ID)
                .title(TITLE)
                .content(CONTENT)
                .stars(STARS)
                .build();
        willReturn(reviewDto).given(reviewService).getReviewById(POKEMON_ID, REVIEW_ID);

        ResultActions response = mockMvc.perform(get("/api/pokemon/" + POKEMON_ID + "/review/" + REVIEW_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    void createReview_shouldReturnCreated() throws Exception {
        ReviewDto reviewDto = ReviewDto.builder()
                .title(TITLE)
                .content(CONTENT)
                .stars(STARS)
                .build();
        ReviewDto createdReviewDto = ReviewDto.builder()
                .id(REVIEW_ID)
                .title(TITLE)
                .content(CONTENT)
                .stars(STARS)
                .build();
        willReturn(createdReviewDto).given(reviewService).createReview(POKEMON_ID, reviewDto);

        ResultActions response = mockMvc.perform(post("/api/pokemon/" + POKEMON_ID + "/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(createdReviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(createdReviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(createdReviewDto.getStars())));
    }

    @Test
    void updateReview_shouldReturnOk() throws Exception {
        ReviewDto reviewDto = ReviewDto.builder()
                .id(REVIEW_ID)
                .title(TITLE)
                .content(CONTENT)
                .stars(STARS)
                .build();
        willReturn(reviewDto).given(reviewService).updateReview(POKEMON_ID, REVIEW_ID, reviewDto);

        ResultActions response = mockMvc.perform(put("/api/pokemon/" + POKEMON_ID + "/review/" + REVIEW_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    void deleteReview_shouldReturnOk() throws Exception {
        willDoNothing().given(reviewService).deleteReview(POKEMON_ID, REVIEW_ID);

        ResultActions response = mockMvc.perform(delete("/api/pokemon/" + POKEMON_ID + "/review/" + REVIEW_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
