package com.tesco.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column private String email;

    public User() {}

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
        return "User{"
                + "id='"
                + id
                + '\''
                + ", username='"
                + username
                + '\''
                + ", mobileNumber='"
                + mobileNumber
                + '\''
                + ", email='"
                + email
                + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
