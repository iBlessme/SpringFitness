package com.example.springfitness.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Abonement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nameAbon, cost, count, full_text;

    public Abonement(String nameAbon, String cost, String count) {
        this.nameAbon = nameAbon;
        this.cost = cost;
        this.count = count;
    }

    public Abonement() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAbon() {
        return nameAbon;
    }

    public void setNameAbon(String nameAbon) {
        this.nameAbon = nameAbon;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }





}
