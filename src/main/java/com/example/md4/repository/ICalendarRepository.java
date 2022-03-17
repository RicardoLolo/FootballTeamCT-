package com.example.md4.repository;

import com.example.md4.model.Calendar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICalendarRepository extends CrudRepository<Calendar, Long> {

    @Query(value = "select * from calendar c \n" +
            "where ?1 \n" +
            "between c.date_start and c.date_finish;", nativeQuery = true)
    Iterable<Calendar> findCalenderToday(String date);
}
