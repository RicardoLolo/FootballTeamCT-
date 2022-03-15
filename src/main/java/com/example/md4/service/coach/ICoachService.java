package com.example.md4.service.coach;

import com.example.md4.model.Coach;
import com.example.md4.model.CoachType;
import com.example.md4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICoachService extends IGeneralService<Coach> {

    Iterable<CoachType> findAllType();

    Iterable<Coach> findAllByName(String name);

    Page<Coach> findPage(Pageable pageable);

    Optional<Coach> findOne(Long id);

    void delete(Long id);
}
