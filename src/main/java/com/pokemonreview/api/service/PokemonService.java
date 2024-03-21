package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.model.Pokemon;

import java.util.Optional;

public interface PokemonService {

    Optional<Pokemon> findById(int pokemonId);
    PokemonResponse getAllPokemons(int pageNo, int pageSize);
    PokemonDto getPokemonById(int pokemonId);
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonDto updatePokemon(int pokemonId, PokemonDto pokemonDto);
    void deletePokemon(int pokemonId);

}
