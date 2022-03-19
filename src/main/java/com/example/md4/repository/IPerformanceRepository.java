package com.example.md4.repository;

import com.example.md4.model.Performance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPerformanceRepository extends CrudRepository<Performance, Long> {
}
