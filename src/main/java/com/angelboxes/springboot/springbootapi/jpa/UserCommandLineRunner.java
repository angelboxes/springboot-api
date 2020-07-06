package com.angelboxes.springboot.springbootapi.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(UserCommandLineRunner.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("Ralph", "Admin"));
        userRepository.save(new User("Ringo", "User"));
        userRepository.save(new User("Paul", "User"));
        userRepository.save(new User("John", "Admin"));

        for (User user : userRepository.findAll()) {
            logger.info(user.toString());
        }
        for (User user : userRepository.findByRole("Admin")) {
            logger.info(user.toString());
        }
    }
}
