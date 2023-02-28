package com.example.demo.controlservices;

import com.example.demo.entities.Hero;
import org.springframework.stereotype.Component;

@Component
public class HeroControlServices {

    private static final int EXP_LVL_BASE = 300;

    public void computeLevelThreshold(Hero hero, int expToAdd){
        this.recursiveLevelComputing(hero, hero.getExpPoint()+expToAdd);
    }

    private void recursiveLevelComputing(Hero hero, int totalExp){
        if(totalExp >= EXP_LVL_BASE*hero.getLevel()){
            totalExp = totalExp-(EXP_LVL_BASE*hero.getLevel());
            hero.setLevel(hero.getLevel()+1);
            hero.setExpPoint(totalExp);
            this.recursiveLevelComputing(hero, totalExp);
        }
    }

}
