package com.example.md4.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "coach")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private LocalDate birthday;
    private String country;
    private double salary;
    private double bonus;
    private String introduction;

    @Transient
    private MultipartFile avaFile;
    private String avatarURL;

    @Transient
    private MultipartFile backGroundFile;
    private String avatarBackGround;
    private String gmail;
    private String passWord;

    @ManyToOne
    @JoinColumn(name = "couchType_id")
    private CoachType coachType;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Set<Role> role;

}
