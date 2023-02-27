package com.example.demo.controller;

import com.example.demo.controller.assembler.HeroModelAssembler;
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

    private final HeroModelAssembler heroModelAssembler;
    public HeroController(HeroRepository heroRepositoryParam, HeroModelAssembler heroModelAssemblerParam) {
        this.heroRepository = heroRepositoryParam;
        this.heroModelAssembler = heroModelAssemblerParam;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/heroes")
    public CollectionModel<EntityModel<Hero>> all() {

        List<EntityModel<Hero>> heroes = heroRepository.findAll().stream().map(heroModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(heroes, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HeroController.class).all()).withSelfRel());
    }

    @GetMapping("/heroes/{id}")
    public EntityModel<Hero> one(@PathVariable Long id) {

        Hero hero = heroRepository.findById(id) //
                .orElseThrow(() -> new HeroNotFoundException(id));

        return heroModelAssembler.toModel(hero);
    }
}
