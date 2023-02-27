package com.example.demo.entities;

import com.example.demo.enums.HeroClass;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private HeroClass heroClass;

    private int expPoint =0;

    public Hero() {
    }

    public Hero(String nameParam, HeroClass heroClassParam) {
        this.name = nameParam;
        this.heroClass = heroClassParam;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
    }

    public int getExpPoint() {
        return expPoint;
    }

    public void setExpPoint(int expPoint) {
        this.expPoint = expPoint;
    }
}
