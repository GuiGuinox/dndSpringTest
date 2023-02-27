package com.example.demo.init;

import com.example.demo.entities.Hero;
import com.example.demo.enums.HeroClass;
import com.example.demo.repository.HeroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(HeroRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Hero("Bilbo Baggins", HeroClass.RODEUR)));
      log.info("Preloading " + repository.save(new Hero("Gandalf", HeroClass.DRUIDE)));
    };
  }
}