package com.example.md4.repository;

import com.example.md4.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusRepository extends CrudRepository<Status, Long> {
}
