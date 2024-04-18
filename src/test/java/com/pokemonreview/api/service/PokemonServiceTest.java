package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.model.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    void getAllPokemons_shouldReturnPokemonResponse() {
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        Page<Pokemon> pokemonPage = new PageImpl<>(List.of(pokemon));
        when(pokemonRepository.findAll(any(Pageable.class))).thenReturn(pokemonPage);

        PokemonResponse pokemonResponse = pokemonService.getAllPokemons(0, 10);

        assertThat(pokemonResponse).isNotNull();
    }

    @Test
    void createPokemon_shouldReturnCreatedPokemonDto() {
        PokemonDto pokemonDto = PokemonDto.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        Pokemon createdPokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        when(pokemonRepository.save(any(Pokemon.class))).thenReturn(createdPokemon);

        PokemonDto createdPokemonDto = pokemonService.createPokemon(pokemonDto);

        assertThat(createdPokemonDto).isNotNull();
    }

}
