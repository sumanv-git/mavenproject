package com.tesco.model;

import java.util.Objects;

public class User {

    private String id;

    private String username;
    private String mobileNumber;
    private String email;

    public User(){}

    public User(String username, String mobileNumber, String email) {
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public User(User existingUser) {
        this.id = existingUser.getId();
        this.username = existingUser.getUsername();
        this.mobileNumber = existingUser.getMobileNumber();
        this.email = existingUser.getEmail();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    // Override equals() to compare users by username
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username); // Compare by username
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
