package com.pokemonreview.api.service.impl;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.model.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    private final static int POKEMON_ID = 1;

    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    void getAllPokemons_shouldReturnPokemonResponse() {
        Pokemon pokemon = mock(Pokemon.class);
        Page<Pokemon> pokemonPage = new PageImpl<>(List.of(pokemon));
        doReturn(pokemonPage).when(pokemonRepository).findAll(any(Pageable.class));

        PokemonResponse pokemonResponse = pokemonService.getAllPokemons(0, 10);

        assertThat(pokemonResponse).isNotNull();
    }

    @Test
    void getPokemonById_shouldReturnPokemonDto() {
        Pokemon pokemon = mock(Pokemon.class);
        doReturn(Optional.of(pokemon)).when(pokemonRepository).findById(POKEMON_ID);

        PokemonDto pokemonDto = pokemonService.getPokemonById(POKEMON_ID);

        assertThat(pokemonDto).isNotNull();
    }

    @Test
    void createPokemon_shouldReturnCreatedPokemonDto() {
        PokemonDto pokemonDto = mock(PokemonDto.class);
        Pokemon pokemon = mock(Pokemon.class);
        when(pokemonRepository.save(any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto createdPokemonDto = pokemonService.createPokemon(pokemonDto);

        assertThat(createdPokemonDto).isNotNull();
    }

    @Test
    void updatePokemon_shouldReturnUpdatedPokemonDto() {
        PokemonDto pokemonDto = mock(PokemonDto.class);
        Pokemon pokemon = mock(Pokemon.class);
        doReturn(Optional.of(pokemon)).when(pokemonRepository).findById(POKEMON_ID);
        doReturn(pokemon).when(pokemonRepository).save(any(Pokemon.class));

        PokemonDto updatedPokemonDto = pokemonService.updatePokemon(POKEMON_ID, pokemonDto);

        assertThat(updatedPokemonDto).isNotNull();
    }

    @Test
    void deletePokemon_shouldCallDelete() {
        Pokemon pokemon = mock(Pokemon.class);
        doReturn(Optional.of(pokemon)).when(pokemonRepository).findById(POKEMON_ID);

        pokemonService.deletePokemon(POKEMON_ID);

        verify(pokemonRepository).delete(pokemon);
    }

}
