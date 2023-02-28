package com.example.demo.controller;

import com.example.demo.controller.assembler.HeroClassModelAssembler;
import com.example.demo.controller.assembler.HeroModelAssembler;
import com.example.demo.controlservices.HeroControlServices;
import com.example.demo.entities.Hero;
import com.example.demo.enums.HeroClass;
import com.example.demo.enums.HeroStatus;
import com.example.demo.repository.HeroRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HeroController {

    private final HeroRepository heroRepository;

    private final HeroModelAssembler heroModelAssembler;

    private final HeroClassModelAssembler heroClassModelAssembler;

    private final HeroControlServices heroControlServices;


    public HeroController(HeroRepository heroRepositoryParam, HeroModelAssembler heroModelAssemblerParam, HeroClassModelAssembler heroClassModelAssembler, HeroControlServices heroControlServices) {
        this.heroRepository = heroRepositoryParam;
        this.heroModelAssembler = heroModelAssemblerParam;
        this.heroClassModelAssembler = heroClassModelAssembler;
        this.heroControlServices = heroControlServices;
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

    @PostMapping("/createHero")
    public ResponseEntity postController(
            @RequestBody Hero hero) {

        this.heroRepository.save(hero);
        return null;
    }

    @GetMapping("/classes")
    public List<EntityModel<HeroClass>> getClasses(){

        List<EntityModel<HeroClass>> classes = Arrays.stream(HeroClass.values()).map(heroClassModelAssembler::toModel).collect(Collectors.toList());

       return classes;
    }

    @PutMapping("/heroes/{id}/addXp/{expPoint}")
    public EntityModel<Hero> addXp(@PathVariable Long id, @PathVariable int expPoint){

        Hero hero = heroRepository.findById(id) //
                .orElseThrow(() -> new HeroNotFoundException(id));

        if(HeroStatus.DEAD.equals(hero.getStatus())){
            throw  new HeroNotFoundException(id);
        }

        this.heroControlServices.computeLevelThreshold(hero, expPoint);

        this.heroRepository.save(hero);

        return this.heroModelAssembler.toModel(hero);

    }

    @PutMapping("/heroes/{id}/kill")
    public EntityModel<Hero> kill(@PathVariable Long id){

        Hero hero = heroRepository.findById(id) //
                .orElseThrow(() -> new HeroNotFoundException(id));

        hero.setStatus(HeroStatus.DEAD);
        this.heroRepository.save(hero);

        return this.heroModelAssembler.toModel(hero);

    }
}
