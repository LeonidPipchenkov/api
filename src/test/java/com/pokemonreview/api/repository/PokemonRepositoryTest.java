package com.pokemonreview.api.repository;

import com.pokemonreview.api.model.Pokemon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void save_shouldReturnSavedPokemon() {
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

}
