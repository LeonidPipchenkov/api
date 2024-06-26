package com.pokemonreview.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.service.PokemonService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTest {

    private final static int ID = 1;
    private final static String NAME = "Pikachu";
    private final static String TYPE = "Electric";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PokemonService pokemonService;

    @Test
    void getPokemons_shouldReturnOk() throws Exception {
        PokemonDto pokemonDto = PokemonDto.builder()
                .name(NAME)
                .type(TYPE)
                .build();
        PokemonResponse pokemonResponse = PokemonResponse.builder()
                .content(List.of(pokemonDto))
                .pageNo(0)
                .pageSize(10)
                .last(true)
                .build();
        willReturn(pokemonResponse).given(pokemonService).getAllPokemons(0, 10);

        ResultActions response = mockMvc.perform(get("/api/pokemon")
                .param("pageNo", "0")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(pokemonResponse.getContent().size())));
    }

    @Test
    void pokemonDetails_shouldReturnOk() throws Exception {
        PokemonDto pokemonDto = PokemonDto.builder()
                .id(ID)
                .name(NAME)
                .type(TYPE)
                .build();
        willReturn(pokemonDto).given(pokemonService).getPokemonById(ID);

        ResultActions response = mockMvc.perform(get("/api/pokemon/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    void createPokemon_shouldReturnCreated() throws Exception {
        PokemonDto pokemonDto = PokemonDto.builder()
                .name(NAME)
                .type(TYPE)
                .build();
        PokemonDto createdPokemonDto = PokemonDto.builder()
                .id(ID)
                .name(NAME)
                .type(TYPE)
                .build();
        willReturn(createdPokemonDto).given(pokemonService).createPokemon(any(PokemonDto.class));

        ResultActions response = mockMvc.perform(post("/api/pokemon/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    void updatePokemon_shouldReturnOk() throws Exception {
        PokemonDto pokemonDto = PokemonDto.builder()
                .id(ID)
                .name(NAME)
                .type(TYPE)
                .build();
        willReturn(pokemonDto).given(pokemonService).updatePokemon(ID, pokemonDto);

        ResultActions response = mockMvc.perform(put("/api/pokemon/" + ID + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    void deletePokemon_shouldReturnOk() throws Exception {
        willDoNothing().given(pokemonService).deletePokemon(ID);

        ResultActions response = mockMvc.perform(delete("/api/pokemon/" + ID + "/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
