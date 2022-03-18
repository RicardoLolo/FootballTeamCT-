package com.example.md4.repository;

import com.example.md4.model.CoachType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoachTypeRepository extends CrudRepository<CoachType, Long> {
}
