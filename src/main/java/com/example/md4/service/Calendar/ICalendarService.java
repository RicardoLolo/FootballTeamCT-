package com.example.md4.service.Calendar;

import com.example.md4.model.Calendar;
import com.example.md4.model.Coach;
import com.example.md4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;


public interface ICalendarService extends IGeneralService<Calendar> {
    Iterable<Calendar> findCalenderToday(String date);

    Page<Calendar> findPage(Pageable pageable);

}
