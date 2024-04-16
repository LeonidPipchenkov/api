package com.pokemonreview.api.repository;

import com.pokemonreview.api.model.Pokemon;
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
public class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    void findAll_shouldReturnPokemonList() {
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        Pokemon pokemon2 = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        assertThat(pokemonList).isNotNull();
        assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    void findById_shouldReturnPokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        pokemonRepository.save(pokemon);

        Pokemon returnedPokemon = pokemonRepository.findById(pokemon.getId()).orElse(null);

        assertThat(returnedPokemon).isNotNull();
    }

    @Test
    void save_shouldReturnSavedPokemon() {
        // Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        // Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        // Assert
        assertThat(savedPokemon).isNotNull();
        assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    void save_shouldReturnUpdatedPokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);

        Pokemon updatedPokemon = null;
        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(pokemon.getId());
        if (optionalPokemon.isPresent()) {
            optionalPokemon.get().setName("Raichu");
            optionalPokemon.get().setType("Electric");
            updatedPokemon = pokemonRepository.save(optionalPokemon.get());
        }

        assertThat(updatedPokemon).isNotNull();
        assertThat(updatedPokemon.getName()).isNotNull();
        assertThat(updatedPokemon.getType()).isNotNull();
    }

    @Test
    void deleteById_shouldDeletePokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> optionalPokemon = pokemonRepository.findById(pokemon.getId());

        assertThat(optionalPokemon).isEmpty();
    }

}
