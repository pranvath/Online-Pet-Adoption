package com.shelter.pet_adoption_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PetAdoptionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetAdoptionApiApplication.class, args);
    }

    // This block automatically adds pets when the app starts
    @Bean
    public CommandLineRunner seedDatabase(PetRepository repository) {
        return args -> {
            if (repository.count() == 0) { // Check if database is empty
                repository.save(new Pet("Bella", "Dog", 3, "Friendly golden retriever, loves kids."));
                repository.save(new Pet("Luna", "Cat", 2, "Quiet and affectionate indoor cat."));
                repository.save(new Pet("Charlie", "Dog", 1, "Energetic beagle pup, needs a yard."));
				repository.save(new Pet("Max", "Dog", 4, "Loyal and protective family dog."));
				repository.save(new Pet("Lola", "Cat", 3, "Playful and curious, loves to explore."));
                System.out.println("Added initial seed data to the database!");
            }
        };
    }
}