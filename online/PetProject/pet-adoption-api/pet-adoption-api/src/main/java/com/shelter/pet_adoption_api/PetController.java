package com.shelter.pet_adoption_api;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "*")
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @PostMapping
    public Pet addPet(@RequestBody Pet newPet) {
        newPet.setAdopted(false);
        return petRepository.save(newPet);
    }

    @PutMapping("/{id}/adopt")
    public Pet markAdopted(@PathVariable int id) {
        Optional<Pet> petToUpdate = petRepository.findById(id);

        if (petToUpdate.isPresent()) {
            Pet pet = petToUpdate.get();
            pet.setAdopted(true);
            return petRepository.save(pet); // Saves the updated status to MySQL
        }
        throw new RuntimeException("Pet not found with ID: " + id);
    }
}