package com.example.md4.service.Calendar;

import com.example.md4.model.Calendar;
import com.example.md4.repository.ICalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalendarService implements ICalendarService{
    @Autowired
    private ICalendarRepository calendarRepository;

    @Override
    public Iterable<Calendar> findAll() {
        return calendarRepository.findListCalender();
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

    @Override
    public Page<Calendar> findPage(Pageable pageable) {
        return calendarRepository.findCalenderPage(pageable);
    }
}
