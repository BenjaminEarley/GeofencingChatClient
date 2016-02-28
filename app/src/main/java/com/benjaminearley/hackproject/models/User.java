package com.benjaminearley.hackproject.models;

public class User {
    private String email;
    private String firsAtName;
    private String lastName;
    private String age;
    private String gender;

    public User() {}

    public String getFirsAtName() {
        return firsAtName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setFirsAtName(String firsAtName) {
        this.firsAtName = firsAtName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
