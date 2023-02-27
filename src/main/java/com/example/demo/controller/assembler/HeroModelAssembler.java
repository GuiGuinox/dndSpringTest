package com.example.demo.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.demo.controller.HeroController;
import com.example.demo.entities.Hero;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public
class HeroModelAssembler implements RepresentationModelAssembler<Hero, EntityModel<Hero>> {

  @Override
  public EntityModel<Hero> toModel(Hero hero) {

    return EntityModel.of(hero, //
        linkTo(methodOn(HeroController.class).one(hero.getId())).withSelfRel(),
        linkTo(methodOn(HeroController.class).all()).withRel("heroes"));
  }
}