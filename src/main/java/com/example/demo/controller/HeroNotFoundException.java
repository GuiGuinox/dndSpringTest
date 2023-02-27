package com.example.demo.controller;


class HeroNotFoundException extends RuntimeException {
    HeroNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}
