package com.example.md4.repository;

import com.example.md4.model.Coach;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ICoachRepository extends PagingAndSortingRepository<Coach, Long> {
    Iterable<Coach> findAllByNameContaining(String name);

    @Query(value = "select * from coach as c where c.gmail = ?1", nativeQuery = true)
    Optional<Coach> findByGmail(String mail);

    @Query(value = "select * from coach as c order by c.id desc limit 1", nativeQuery = true)
    Coach findCoachLast();

    @Query(value = "select sum(c.salary) from coach as c ;", nativeQuery = true)
    Double totalCoachSalary();

    @Query(value = "select sum(c.bonus) from coach as c ;", nativeQuery = true)
    Double totalCoachBonus();
}
