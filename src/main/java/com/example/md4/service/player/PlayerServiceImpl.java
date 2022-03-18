package com.example.md4.service.player;

import com.example.md4.model.Performance;
import com.example.md4.model.Player;
import com.example.md4.model.Position;
import com.example.md4.model.Status;
import com.example.md4.repository.IPerformanceRepository;
import com.example.md4.repository.IPlayerRepository;
import com.example.md4.repository.IPositionRepository;
import com.example.md4.repository.IStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    private IPlayerRepository playerRepository;
    @Autowired
    private IPerformanceRepository performanceRepository;
    @Autowired
    private IPositionRepository positionRepository;
    @Autowired
    private IStatusRepository statusRepository;

    @Override
    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public void remove(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Iterable<Position> findAllPosition() {
        return positionRepository.findAll();
    }

    @Override
    public Iterable<Performance> findAllPerformance() {
        return performanceRepository.findAll();
    }

    @Override
    public Iterable<Status> findAllStatus() {
        return statusRepository.findAll();
    }

    @Override
    public Iterable<Player> findAllByName(String name) {
        return playerRepository.findAllByNameContaining(name);
    }

    @Override
    public Page<Player> findPage(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }
}
