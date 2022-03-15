package com.example.md4.repository;

import com.example.md4.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByName(String username);
}
