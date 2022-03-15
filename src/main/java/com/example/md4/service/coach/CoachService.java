package com.example.md4.service.coach;

import com.example.md4.model.Coach;
import com.example.md4.model.CoachType;
import com.example.md4.repository.ICoachRepository;
import com.example.md4.repository.ICoachTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Coach save(Coach coach) {
       return coachRepository.save(coach);

    }

    @Override
    public void delete(Long id) {
        coachRepository.deleteById(id);
    }

    @Override
    public Iterable<CoachType> findAllType() {
        return coachTypeRepository.findAll();
    }

    @Override
    public Iterable<Coach> findAllByName(String name) {
       return coachRepository.findAllByNameContaining(name);
    }

    @Override
    public Page<Coach> findPage(Pageable pageable) {
        return coachRepository.findAll(pageable);
    }
}
