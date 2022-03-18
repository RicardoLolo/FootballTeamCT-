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
    private Long id;
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
    @Column(unique = true, nullable = false)
    private String gmail;
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "couchType_id")
    private CoachType coachType;

   public Long getId(){
       return id;
   }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public MultipartFile getAvaFile() {
        return avaFile;
    }

    public void setAvaFile(MultipartFile avaFile) {
        this.avaFile = avaFile;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public MultipartFile getBackGroundFile() {
        return backGroundFile;
    }

    public void setBackGroundFile(MultipartFile backGroundFile) {
        this.backGroundFile = backGroundFile;
    }

    public String getAvatarBackGround() {
        return avatarBackGround;
    }

    public void setAvatarBackGround(String avatarBackGround) {
        this.avatarBackGround = avatarBackGround;
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

    public CoachType getCoachType() {
        return coachType;
    }

    public void setCoachType(CoachType coachType) {
        this.coachType = coachType;
    }


}
