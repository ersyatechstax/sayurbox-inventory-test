package com.main.persistence.repository;

import com.main.persistence.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * created by ersya 30/03/2019
 */
@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
}
