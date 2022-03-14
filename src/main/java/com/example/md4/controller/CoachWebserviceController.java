package com.example.md4.controller;

import com.example.md4.model.Coach;
import com.example.md4.model.CoachType;
import com.example.md4.service.ICoachService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity<Iterable<Coach>> showAll() {
        Iterable<Coach> coaches = coachService.findAll();
        if (!coaches.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }
    @GetMapping("/type")
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
}
