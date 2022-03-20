package com.example.md4.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthday;
    private String country;
    private double height;
    private double weight;
    private double BMI;//W/(H*H)
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
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public Player() {
    }

    public Long getId() {
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
