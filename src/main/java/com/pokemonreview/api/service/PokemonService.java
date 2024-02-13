package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;

public interface PokemonService {

    PokemonResponse getAllPokemons(int pageNo, int pageSize);
    PokemonDto getPokemonById(int pokemonId);
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonDto updatePokemon(int pokemonId, PokemonDto pokemonDto);
    void deletePokemon(int pokemonId);

}
