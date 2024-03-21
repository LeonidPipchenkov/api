package com.pokemonreview.api.service;

import com.pokemonreview.api.model.Role;
import com.pokemonreview.api.model.UserEntity;

import java.util.Optional;

public interface UserService {

    boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
    void save(UserEntity user);
    Optional<Role> findRoleByName(String name);

}
