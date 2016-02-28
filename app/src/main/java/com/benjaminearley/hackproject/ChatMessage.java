package com.benjaminearley.hackproject;

public class ChatMessage {

    private String imageUrl;
    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    private String userUuid;
    private String message;

    public ChatMessage() {
    }


    public ChatMessage(String message) {

        this.message = message;
    }

    public ChatMessage(String imageUrl, String firstName, String lastName, String age, String gender, String message) {
        this.imageUrl = imageUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

}
