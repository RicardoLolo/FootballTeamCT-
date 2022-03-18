package com.example.md4.repository;

import com.example.md4.model.Coach;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICoachRepository extends PagingAndSortingRepository<Coach, Long> {
    Iterable<Coach> findAllByNameContaining(String name);

    @Query(value = "select * from coach as c order by c.id desc limit 1", nativeQuery = true)
    Coach findCoachLast();
    @Query(value = "select * from account where coach_id", nativeQuery = true)
    Coach editCoach();

}
