package com.example.md4.service.impl;

import com.example.md4.model.Coach;
import com.example.md4.model.CoachType;
import com.example.md4.repository.ICoachRepository;
import com.example.md4.repository.ICoachTypeRepository;
import com.example.md4.service.ICoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoachService implements ICoachService {
    @Autowired
    private ICoachRepository coachRepository;
    @Autowired
    private ICoachTypeRepository coachTypeRepository;

    @Override
    public Iterable<Coach> findAll() {
        return coachRepository.findAll();
    }

    @Override
    public Optional<Coach> findOne(Long id) {
        return coachRepository.findById(id);
    }

    @Override
    public String save(Coach coach) {
        coachRepository.save(coach);
        return null;
    }

    @Override
    public void delete(Long id) {
        coachRepository.deleteById(id);
    }

    @Override
    public Iterable<CoachType> findAllType() {
        return coachTypeRepository.findAll();
    }
}
