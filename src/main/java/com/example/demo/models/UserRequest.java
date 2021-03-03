package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name="requests")
public class UserRequest {
    @Column(name = "name",nullable = false, length = 45)
    private String name;
    @Column(name = "lastName",nullable = false, length = 45)
    private String lastName;
    @Column(name = "mail",nullable = false, length = 45)
    private String mail;
    @Column(name = "phone",nullable = false, length = 45)
    private String phone;
    @Column(name = "brand",nullable = false, length = 45)
    private String brand;
    @Column(name = "user",nullable = false, length = 45)
    private String username;
    @Column(name = "pass",nullable = false, length = 200)
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UserRequest(String firstName, String lastName, String mail, String phone,String brand, String username, String password) {
        this.name = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phone = phone;
        this.brand = brand;
        this.username = username;
        this.password = password;
    }

    public UserRequest() {

    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
