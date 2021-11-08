package com.example.springfitness.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Sity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, message = "Диапазон от 5")
    private String name;

    @ManyToOne(optional = false)
    private User user;

    public Sity(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Sity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
