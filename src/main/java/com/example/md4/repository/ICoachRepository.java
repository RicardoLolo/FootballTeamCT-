package com.example.md4.repository;

import com.example.md4.model.Coach;
import org.springframework.data.jdbc.repository.query.Query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ICoachRepository extends PagingAndSortingRepository<Coach, Long> {

 @Query(value = "select p  from coach c  order c.id desc limit 1 , nativeQuery = true")
    Coach findCoachLast();

 Iterable<Coach> findAllByNameContaining(String name);
}
