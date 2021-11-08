package com.example.springfitness.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, message = "Диапазон от 5")
    private String name;

    @NotNull(message = "Не может быть пустым")
    private Integer countPeople;

    @ManyToOne(optional = false)
    private Sity sity;

    public Hall(String name, Integer countPeople, Sity sity) {
        this.name = name;
        this.countPeople = countPeople;
        this.sity = sity;
    }

    public Hall() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCountPeople() {
        return countPeople;
    }

    public void setCountPeople(Integer countPeople) {
        this.countPeople = countPeople;
    }

    public Sity getSity() {
        return sity;
    }

    public void setSity(Sity sity) {
        this.sity = sity;
    }
}
