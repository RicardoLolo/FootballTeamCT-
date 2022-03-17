package com.example.md4.service.Calendar;

import com.example.md4.model.Calendar;
import com.example.md4.service.IGeneralService;

import java.time.LocalDateTime;


public interface ICalendarService extends IGeneralService<Calendar> {
    Iterable<Calendar> findCalenderToday(String date);

}
