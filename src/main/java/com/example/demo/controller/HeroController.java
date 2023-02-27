package com.example.demo.controller;

import com.example.demo.entities.Hero;
import com.example.demo.repository.HeroRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HeroController {

    private final HeroRepository heroRepository;

    public HeroController(HeroRepository heroRepositoryParam) {
        this.heroRepository = heroRepositoryParam;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/heroes")
    CollectionModel<EntityModel<Hero>> all() {

        List<EntityModel<Hero>> heroes = heroRepository.findAll().stream().map(hero -> EntityModel.of(hero, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HeroController.class).one(hero.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HeroController.class).all()).withRel("heroes")))
                .collect(Collectors.toList());

        return CollectionModel.of(heroes, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HeroController.class).all()).withSelfRel());
    }

    @GetMapping("/heroes/{id}")
    EntityModel<Hero> one(@PathVariable Long id) {

        Hero employee = heroRepository.findById(id) //
                .orElseThrow(() -> new HeroNotFoundException(id));

        return EntityModel.of(employee, //
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HeroController.class).one(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HeroController.class).all()).withRel("employees"));
    }
}
