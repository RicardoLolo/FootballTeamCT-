package com.example.md4.service.player;

import com.example.md4.model.Performance;
import com.example.md4.model.Player;
import com.example.md4.model.Position;
import com.example.md4.model.Status;
import com.example.md4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;

public interface IPlayerService extends IGeneralService<Player> {

    Iterable<Position> findAllPosition();

    Iterable<Performance> findAllPerformance();

    Iterable<Status> findAllStatus();

    Iterable<Player> findAllByName(String name);

    Page<Player> findPage(Pageable pageable);
}
