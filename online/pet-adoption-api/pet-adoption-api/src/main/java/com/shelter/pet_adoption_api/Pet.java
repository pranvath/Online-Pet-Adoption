package com.shelter.pet_adoption_api;

public class Pet {
    private int id;
    private String name;
    private String type;
    private int age;
    private String description;
    private boolean isAdopted;

    // Constructors
    public Pet() {} // Required by Spring to convert JSON

    public Pet(int id, String name, String type, int age, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.age = age;
        this.description = description;
        this.isAdopted = false;
    }

    // Getters and Setters (Spring needs these to generate JSON)
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