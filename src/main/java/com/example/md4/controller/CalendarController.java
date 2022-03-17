package com.example.md4.controller;

import com.example.md4.model.Calendar;
import com.example.md4.service.Calendar.ICalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/calendar")
public class CalendarController {
    @Autowired
    private ICalendarService calendarService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Calendar>> listsCalendar() {
        Iterable<Calendar> calendars = calendarService.findAll();
        return new ResponseEntity<>(calendars, HttpStatus.OK);
    }

    @PostMapping("/save_calendar")
    public ResponseEntity<Calendar> saveCalendar(@RequestBody Calendar calendar) {
        Calendar calendarNew = calendarService.save(calendar);
        return new ResponseEntity<>(calendarNew, HttpStatus.OK);
    }

    @DeleteMapping("/delete_calendar/{id}")
    public ResponseEntity<?> deleteCalendarById(@PathVariable("id") Long id){
        Optional<Calendar> calendar = calendarService.findById(id);
        if (!calendar.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        calendarService.remove(id);
        return new ResponseEntity<>(calendar.get(), HttpStatus.OK);
    }

    @GetMapping("/event_calendar/{id}")
    public ResponseEntity<Optional<Calendar>> getById(@PathVariable("id") Long id) {
        Optional<Calendar> calendar = calendarService.findById(id);
        if (!calendar.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(calendar, HttpStatus.OK);
    }

    @PutMapping("/edit_calendar/{id}")
    public ResponseEntity<Calendar> editProduct(@RequestBody Calendar calendar_new, @PathVariable("id") Long id) {
        Optional<Calendar> calendar = calendarService.findById(id);
        if (!calendar.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        calendar_new.setId(calendar.get().getId());
        calendarService.save(calendar_new);
        return new ResponseEntity<>(calendar_new, HttpStatus.OK);
    }

    @GetMapping("/list_today")
    public ResponseEntity<Iterable<Calendar>> listsTodayCalendar() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Iterable<Calendar> calendar_today = calendarService.findCalenderToday(dtf.format(now));
        return new ResponseEntity<>(calendar_today, HttpStatus.OK);
    }
}
