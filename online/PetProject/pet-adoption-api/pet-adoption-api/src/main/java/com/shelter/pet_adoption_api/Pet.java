package com.shelter.pet_adoption_api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    private String type;
    private int age;
    private String description;
    private boolean isAdopted;

    // Constructors
    public Pet() {} 

    public Pet(String name, String type, int age, String description) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.description = description;
        this.isAdopted = false;
    }

    // Keep all your existing Getters and Setters here...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isAdopted() { return isAdopted; }
    public void setAdopted(boolean adopted) { isAdopted = adopted; }
}