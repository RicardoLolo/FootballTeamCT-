package com.example.md4.service.Calendar;

import com.example.md4.model.Calendar;
import com.example.md4.repository.ICalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CalendarService implements ICalendarService{
    @Autowired
    private ICalendarRepository calendarRepository;

    @Override
    public Iterable<Calendar> findAll() {
        return calendarRepository.findAll();
    }

    @Override
    public Optional<Calendar> findById(Long id) {
        return calendarRepository.findById(id);
    }

    @Override
    public Calendar save(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    @Override
    public void remove(Long id) {
        calendarRepository.deleteById(id);
    }

    @Override
    public Iterable<Calendar> findCalenderToday(String date) {
        return calendarRepository.findCalenderToday(date);
    }
}
