package com.example.md4.service.coach;

import com.example.md4.model.Coach;
import com.example.md4.model.CoachType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;

import java.util.Optional;

public interface ICoachService {


    Iterable<Coach> findAll();

    Optional<Coach> findOne(Long id);

    Coach save(Coach coach);

    void delete(Long id);

    Iterable<CoachType> findAllType();

    Iterable<Coach> findAllByName(String name);

    Page<Coach> findPage(Pageable pageable);

}
