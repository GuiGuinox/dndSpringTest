package com.example.demo.controller;


public class HeroNotFoundException extends RuntimeException {
    HeroNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}
