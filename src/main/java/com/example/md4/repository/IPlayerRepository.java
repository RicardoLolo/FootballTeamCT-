package com.example.md4.repository;

import com.example.md4.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPlayerRepository extends JpaRepository<Player, Long> {

    @Query(value = "select * from player as p order by p.id desc limit 1;", nativeQuery = true)
    Player findPlayerLast();

    @Query(value = "select * from player as p where p.gmail = ?;", nativeQuery = true)
    Optional<Player> findByGmail(String username);

    Iterable<Player> findAllByNameContaining(String name);
}
