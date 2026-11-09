package com.shelter.pet_adoption_api;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "*") // Allows your HTML frontend to connect
public class PetController {

    // Our temporary database (replaces the static list from the console app)
    private List<Pet> pets = new ArrayList<>();
    private int currentId = 4; // Starting at 4 because we have 3 seeded pets

    // Seed initial data just like the console app
    public PetController() {
        pets.add(new Pet(1, "Bella", "Dog", 3, "Friendly golden retriever, loves kids."));
        pets.add(new Pet(2, "Luna", "Cat", 2, "Quiet and affectionate indoor cat."));
        pets.add(new Pet(3, "Charlie", "Dog", 1, "Energetic beagle pup, needs a yard."));
        pets.add(new Pet(4, "Max", "Dog", 3, "short small"));
        pets.add(new Pet(5, "Milo", "Cat", 2, "playful and curious"));
    }

    // Matches Menu Option 1: View all pets
    // Triggers when frontend calls GET http://localhost:8080/api/pets
    @GetMapping
    public List<Pet> getAllPets() {
        return pets;
    }

    // Matches Menu Option 3: Add a new pet listing
    // Triggers when frontend calls POST http://localhost:8080/api/pets
    @PostMapping
    public Pet addPet(@RequestBody Pet newPet) {
        newPet.setId(currentId++);
        newPet.setAdopted(false);
        pets.add(newPet);
        return newPet;
    }

    // Matches Staff Action: Mark pet adopted
    // Triggers when frontend calls PUT http://localhost:8080/api/pets/{id}/adopt
    @PutMapping("/{id}/adopt")
    public Pet markAdopted(@PathVariable int id) {
        Optional<Pet> petToUpdate = pets.stream()
            .filter(p -> p.getId() == id)
            .findFirst();

        if (petToUpdate.isPresent()) {
            Pet pet = petToUpdate.get();
            pet.setAdopted(true);
            return pet;
        }
        throw new RuntimeException("Pet not found with ID: " + id);
    }
}

