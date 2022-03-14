package com.example.md4.repository;

import com.example.md4.model.Coach;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoachRepository extends CrudRepository<Coach, Long> {

}
