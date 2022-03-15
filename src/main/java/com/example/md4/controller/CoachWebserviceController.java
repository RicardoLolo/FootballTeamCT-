package com.example.md4.controller;

import com.example.md4.model.Coach;
import com.example.md4.model.CoachType;
;
import com.example.md4.service.coach.ICoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/coaches")
public class CoachWebserviceController {
    @Autowired
    private ICoachService coachService;

    @GetMapping("/page")
    public ResponseEntity<Page<Coach>> showAllPage(@PageableDefault(value = 2) Pageable pageable) {
        Page<Coach> coaches = coachService.findPage(pageable);
        if (!coaches.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Coach>> showAll() {
        Iterable<Coach> coaches = coachService.findAll();
        if (!coaches.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }
    @GetMapping("/coachType")
    public ResponseEntity<Iterable<CoachType>> showAllType() {
        Iterable<CoachType> coachTypes = coachService.findAllType();
        if (!coachTypes.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coachTypes, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Coach> delete(@PathVariable("id") Long id) {
        Optional<Coach> coach = coachService.findOne(id);
        if (!coach.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        coachService.delete(id);
        return new ResponseEntity<>(coach.get(), HttpStatus.OK);

    }
    @GetMapping("/search")
    public ResponseEntity<Iterable<Coach>> showAllByName(@RequestParam("search") String search) {
        Iterable<Coach> products = coachService.findAllByName(search);
        if (!products.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
