package com.example.md4.service.player;

import com.example.md4.model.Performance;
import com.example.md4.model.Player;
import com.example.md4.model.Position;
import com.example.md4.model.Status;
import com.example.md4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPlayerService extends IGeneralService<Player> {

    Iterable<Position> findAllPosition();

    Iterable<Performance> findAllPerformance();

    Iterable<Status> findAllStatus();

    Optional<Player> findByGmail(String mail);

    Iterable<Player> findAllByName(String name);

    Page<Player> findPage(Pageable pageable);
}
