package com.example.demo.controller.assembler;

import com.example.demo.controller.HeroController;
import com.example.demo.entities.Hero;
import com.example.demo.enums.HeroClass;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public
class HeroClassModelAssembler implements RepresentationModelAssembler<HeroClass, EntityModel<HeroClass>> {

  @Override
  public EntityModel<HeroClass> toModel(HeroClass hero) {

    return EntityModel.of(hero,
        linkTo(methodOn(HeroController.class).all()).withRel("classes"));
  }
}