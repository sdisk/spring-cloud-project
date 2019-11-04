package com.hq.model.entity;

import java.io.Serializable;

/**
 * @program: spring-cloud-project
 * @description:
 * @author: Mr.Huang
 * @create: 2019-11-04 15:02
 **/
public class User implements Serializable {

    private static final long serialVersionUID = 7024785034980267809L;

    private int id;
    private String username;
    private int age;
    private String email;

    public User() {
    }

    public User(int id, String username, int age, String email) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", age=" + age + ", email='" + email + '\'' + '}';
    }
}
