package com.example.md4.repository;

import com.example.md4.model.Calendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICalendarRepository extends PagingAndSortingRepository<Calendar, Long> {

    @Query(value = "select * from calendar c \n" +
            "where ?1 \n" +
            "between c.date_start and c.date_finish;", nativeQuery = true)
    Iterable<Calendar> findCalenderToday(String date);

    @Query(value = "select * from calendar c \n" +
            "order by c.date_start asc;", nativeQuery = true)
    Iterable<Calendar> findListCalender();

    @Query(value = "delete from calendar c where c.date_finish < ?1;", nativeQuery = true)
    void deleteCalenderToday(String date);

    @Query(value = "select * from calendar c\n" +
            "    order by c.date_start;", nativeQuery = true)
    Page<Calendar> findCalenderPage(Pageable pageable);

}
