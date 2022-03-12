package com.example.md4.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String gmail;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Set<Role> role;
}