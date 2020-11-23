package com.ringodev.factory;

import com.ringodev.factory.data.Constraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;




@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CommandLineRunnerImpl.class);

    ConstraintRepository constraintRepository;

    @Autowired
    CommandLineRunnerImpl(ConstraintRepository constraintRepository){
        this.constraintRepository = constraintRepository;
    }

    @Override
    public void run(String... args) {

        logger.info("Running CommandLineRunnerImpl");

        addConstraints();

    }

    private void addConstraints(){

        // Bedingung 1
        this.constraintRepository.save(new Constraint("Flatbarlenker","Kunststoff"));
        this.constraintRepository.save(new Constraint("Rennradlenker","Kunststoff"));

        // Bedingung 3
        this.constraintRepository.save(new Constraint("Stahl","Nabenschaltung"));
        this.constraintRepository.save(new Constraint("Stahl","Tretlagerschaltung"));

        // Bedingung 4
        this.constraintRepository.save(new Constraint("Kunststoffgriff","Stahl"));
        this.constraintRepository.save(new Constraint("Kunststoffgriff","Aluminium"));
        this.constraintRepository.save(new Constraint("Ledergriff","Flatbarlenker"));
        this.constraintRepository.save(new Constraint("Ledergriff","Bullhornlenker"));

    }

}
