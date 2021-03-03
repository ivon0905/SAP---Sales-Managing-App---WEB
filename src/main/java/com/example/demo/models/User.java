package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User{
    @Column(name = "username",nullable = false, length = 45)
    private String username;
    @Column(name = "password",nullable = false, length = 68)
    private String password;
    @Column(name = "app_role",nullable = false, length = 45)
    private String appRole;
    @Column(name="email",nullable = false, length = 45)
    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false, length = 45)
    private Long id;
    @Column(name = "brand")
    private String brand;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;

    public User(){
        super();
    }

    public User(String username, String password, String email,String appRole, Long id, String brand, String name, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.appRole = appRole;
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUsername() {
        return username;
    }

    public String getAppRole() {
        return appRole;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAppRole(String appRole) {
        this.appRole = appRole;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
