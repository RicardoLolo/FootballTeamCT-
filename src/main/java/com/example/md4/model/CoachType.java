package com.example.md4.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "coachType")
public class CoachType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
}
