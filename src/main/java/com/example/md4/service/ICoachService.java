package com.example.md4.service;

import com.example.md4.model.Coach;
import com.example.md4.model.CoachType;

import java.util.Optional;

public interface ICoachService {
    Iterable<Coach> findAll();

    Optional<Coach> findOne(Long id);

    String save(Coach coach);

    void delete(Long id);

    Iterable<CoachType> findAllType();
}
