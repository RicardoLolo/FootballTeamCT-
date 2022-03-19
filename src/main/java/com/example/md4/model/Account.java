package com.example.md4.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String gmail;
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "acc_roles",
            joinColumns = {@JoinColumn(name = "acc_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}