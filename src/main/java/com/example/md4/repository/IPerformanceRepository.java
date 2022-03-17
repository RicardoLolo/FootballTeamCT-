package com.example.md4.repository;

import com.example.md4.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPerformanceRepository extends JpaRepository<Performance, Long> {
}
