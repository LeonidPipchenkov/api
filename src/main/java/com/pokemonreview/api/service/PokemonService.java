package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;

import java.util.List;

public interface PokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);
    List<PokemonDto> getAllPokemons();
    PokemonDto getPokemonById(int pokemonId);
    PokemonDto updatePokemon(int pokemonId, PokemonDto pokemonDto);

}
