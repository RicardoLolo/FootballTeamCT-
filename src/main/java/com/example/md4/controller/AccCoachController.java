package com.example.md4.controller;

import com.example.md4.model.Account;
import com.example.md4.model.Coach;
import com.example.md4.repository.ICoachRepository;
import io.jsonwebtoken.impl.AbstractTextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AccCoachController {
    @Autowired
    private ICoachRepository coachRepository;
    @PostMapping
    public ResponseEntity<Coach> createCoach(@RequestBody Coach coach, AbstractTextCodec passwordEncoder) {
        Coach coachNew = coachRepository.findCoachLast();
        new Account(coachNew.getGmail(), passwordEncoder.encode(coachNew.getPassword()), coachNew.getId(), 2);
        return new ResponseEntity<>(coachNew, HttpStatus.CREATED);
    }
}
