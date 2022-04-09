package com.example.weatherapp.dto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "people",uniqueConstraints = @UniqueConstraint(columnNames={"username"}))
public class User /*implements UserDetails*/
 {
    @Column(name = "id")
    @Id
    @SequenceGenerator( name = "userIdGenerator",
            sequenceName = "user_id_seq",
            schema = "public",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userIdGenerator")
    private Long id;
    @Column(name = "username",nullable = false)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "active",nullable = false)
    private boolean active;


    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

     public void setId(Long id) {
         this.id = id;
     }

     public String getUsername() {
         return username;
     }

     public String getPassword() {
         return password;
     }

     public void setActive(boolean active) {
        this.active = active;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

 }

