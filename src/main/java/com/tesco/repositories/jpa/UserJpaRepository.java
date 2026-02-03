package com.tesco.repositories.jpa;

import com.tesco.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
