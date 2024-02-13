package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;

import java.util.List;

public interface PokemonService {

    List<PokemonDto> getAllPokemons();
    PokemonDto getPokemonById(int pokemonId);
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonDto updatePokemon(int pokemonId, PokemonDto pokemonDto);
    void deletePokemon(int pokemonId);

}
