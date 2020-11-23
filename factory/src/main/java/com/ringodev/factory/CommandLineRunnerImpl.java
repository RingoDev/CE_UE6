package com.ringodev.factory;

import com.ringodev.factory.data.Restriction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;




@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CommandLineRunnerImpl.class);

    RestrictionRepository restrictionRepository;

    @Autowired
    CommandLineRunnerImpl(RestrictionRepository restrictionRepository){
        this.restrictionRepository = restrictionRepository;
    }

    @Override
    public void run(String... args) {

        logger.info("Running CommandLineRunnerImpl");

        addConstraints();

        logger.info("Exiting CommandLineRunnerImpl");

    }

    private void addConstraints(){


        // Bedingung 1
        this.restrictionRepository.save(new Restriction("Flatbarlenker","Kunststoff"));
        this.restrictionRepository.save(new Restriction("Rennradlenker","Kunststoff"));

        // Bedingung 3
        this.restrictionRepository.save(new Restriction("Stahl","Nabenschaltung"));
        this.restrictionRepository.save(new Restriction("Stahl","Tretlagerschaltung"));

        // Bedingung 4
        this.restrictionRepository.save(new Restriction("Kunststoffgriff","Stahl"));
        this.restrictionRepository.save(new Restriction("Kunststoffgriff","Aluminium"));
        this.restrictionRepository.save(new Restriction("Ledergriff","Flatbarlenker"));
        this.restrictionRepository.save(new Restriction("Ledergriff","Bullhornlenker"));

    }

}
