package com.example.md4.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String state;
}
