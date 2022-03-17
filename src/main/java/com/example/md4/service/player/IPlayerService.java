package com.example.md4.service.player;

import com.example.md4.model.Performance;
import com.example.md4.model.Player;
import com.example.md4.model.Position;
import com.example.md4.model.Status;
import com.example.md4.service.IGeneralService;
import org.springframework.data.jdbc.repository.query.Query;

public interface IPlayerService extends IGeneralService<Player> {
    @Query(value = "select * from player as p order p.id desc limit 1, nativeQuery=true ")
    Player findPlayerLast(Long id);

    Iterable<Position> findAllPosition();

    Iterable<Performance> findAllPerformance();

    Iterable<Status> findAllStatus();

    Iterable<Player> findAllByName(String name);
}
