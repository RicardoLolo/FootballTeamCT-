package com.example.md4.repository;

import com.example.md4.model.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPositionRepository extends CrudRepository<Position, Long> {
}
