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
    @Query(value = "select coach.id, coach.gmail, coach.password\n" +
            "from coach inner join account a2 on coach.id = a2.coach_id where coach_id = ?", nativeQuery = true)
    Coach editCoach();

}
