import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Pet {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String type;
    private int age;
    private String description;
    private boolean isAdopted;

    public Pet(String name, String type, int age, String description) {
        this.id = idCounter++;
        this.name = name;
        this.type = type;
        this.age = age;
        this.description = description;
        this.isAdopted = false;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public boolean isAdopted() { return isAdopted; }
    
    public void markAdopted() { this.isAdopted = true; }

    @Override
    public String toString() {
        String status = isAdopted ? "[ADOPTED]" : "[AVAILABLE]";
        return String.format("%d. %s - %s (%s, %d years old) : %s", 
                id, status, name, type, age, description);
    }
}

class AdoptionRequest {
    private static int idCounter = 1;
    private int requestId;
    private int petId;
    private String requesterName;
    private String contactInfo;

    public AdoptionRequest(int petId, String requesterName, String contactInfo) {
        this.requestId = idCounter++;
        this.petId = petId;
        this.requesterName = requesterName;
        this.contactInfo = contactInfo;
    }

    public int getRequestId() { return requestId; }
    public int getPetId() { return petId; }

    @Override
    public String toString() {
        return String.format("Request #%d | Pet ID: %d | Requester: %s | Contact: %s", 
                requestId, petId, requesterName, contactInfo);
    }
}

public class PetAdoptionSystem {
    private static List<Pet> pets = new ArrayList<>();
    private static List<AdoptionRequest> requests = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        boolean running = true;

        System.out.println("Welcome to the Online Pet Adoption System!");

        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. View all pets (Browse & Filter)");
            System.out.println("2. Submit an adoption request");
            System.out.println("3. Staff: Add a new pet listing");
            System.out.println("4. Staff: Review requests & mark pet adopted");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": browsePets(); break;
                case "2": submitRequest(); break;
                case "3": addPet(); break;
                case "4": reviewRequests(); break;
                case "5": running = false; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Exiting system. Goodbye!");
    }

    private static void browsePets() {
        System.out.println("\n--- Browse Pets ---");
        System.out.println("1. Show all");
        System.out.println("2. Filter by Type (e.g., Dog, Cat)");
        System.out.println("3. Filter by Status (Available/Adopted)");
        System.out.print("Choose filter: ");
        String filterChoice = scanner.nextLine();

        List<Pet> filteredPets = new ArrayList<>(pets);

        if (filterChoice.equals("2")) {
            System.out.print("Enter pet type: ");
            String type = scanner.nextLine().trim();
            filteredPets = pets.stream()
                .filter(p -> p.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
        } else if (filterChoice.equals("3")) {
            System.out.print("Enter status (1 for Available, 2 for Adopted): ");
            String status = scanner.nextLine();
            boolean checkAdopted = status.equals("2");
            filteredPets = pets.stream()
                .filter(p -> p.isAdopted() == checkAdopted)
                .collect(Collectors.toList());
        }

        if (filteredPets.isEmpty()) {
            System.out.println("No pets found matching criteria.");
        } else {
            System.out.println("\n--- Pet Listings ---");
            for (Pet p : filteredPets) {
                System.out.println(p);
            }
        }
    }

    private static void submitRequest() {
        System.out.print("\nEnter the ID of the pet you want to adopt: ");
        int petId = Integer.parseInt(scanner.nextLine());

        Pet selectedPet = getPetById(petId);

        if (selectedPet == null) {
            System.out.println("Pet not found.");
            return;
        }

        if (selectedPet.isAdopted()) {
            System.out.println("Sorry, this pet has already been adopted. We cannot accept new requests for them.");
            return;
        }

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your contact info (email/phone): ");
        String contact = scanner.nextLine();

        requests.add(new AdoptionRequest(petId, name, contact));
        System.out.println("Adoption request submitted successfully!");
    }

    private static void addPet() {
        System.out.print("\nEnter pet name: ");
        String name = scanner.nextLine();
        System.out.print("Enter pet type (e.g., Dog, Cat): ");
        String type = scanner.nextLine();
        System.out.print("Enter pet age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter short description: ");
        String desc = scanner.nextLine();

        pets.add(new Pet(name, type, age, desc));
        System.out.println("Pet listing added successfully!");
    }

    private static void reviewRequests() {
        System.out.println("\n--- Pending Adoption Requests ---");
        if (requests.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (AdoptionRequest req : requests) {
            System.out.println(req);
        }

        System.out.print("\nEnter the Request ID to approve (or 0 to cancel): ");
        int reqId = Integer.parseInt(scanner.nextLine());

        if (reqId == 0) return;

        AdoptionRequest approvedReq = null;
        for (AdoptionRequest req : requests) {
            if (req.getRequestId() == reqId) {
                approvedReq = req;
                break;
            }
        }

        if (approvedReq != null) {
            Pet pet = getPetById(approvedReq.getPetId());
            if (pet != null && !pet.isAdopted()) {
                pet.markAdopted();
                System.out.println("Success! " + pet.getType() + " has been marked as adopted.");
                
                // Optional: Clear other requests for this pet now that it's adopted
                requests.removeIf(r -> r.getPetId() == pet.getId());
            } else {
                System.out.println("Error: Pet already adopted or not found.");
            }
        } else {
            System.out.println("Request ID not found.");
        }
    }

    private static Pet getPetById(int id) {
        for (Pet p : pets) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private static void seedData() {
        pets.add(new Pet("Bella", "Dog", 3, "Friendly golden retriever, loves kids."));
        pets.add(new Pet("Luna", "Cat", 2, "Quiet and affectionate indoor cat."));
        pets.add(new Pet("Charlie", "Dog", 1, "Energetic beagle pup, needs a yard."));
    }
}