package com.example.demo.repository;

import com.example.demo.entities.Hero;
import com.example.demo.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface HeroRepository extends JpaRepository<Hero, Long> {

    List<Hero> findByName(@Param("name") String name);

}

