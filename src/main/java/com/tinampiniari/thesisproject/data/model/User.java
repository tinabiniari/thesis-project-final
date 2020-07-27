package com.tinampiniari.thesisproject.data.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user_details")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Please provide an email")
    private String email;

    @Size(min=5,message = "Your password should be at least 5 characters")
    @NotEmpty(message = "Please provide a password")
    private String password;

    @NotEmpty(message = "Please provide a name")
    private String firstName;

    @NotEmpty(message = "Please provide a surname")
    private  String lastName;

    private int active;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Role role;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_profile",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name="profile_id"))
    private Profile profile;

    @ElementCollection
    private List<Long> tags = new ArrayList<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }
}
