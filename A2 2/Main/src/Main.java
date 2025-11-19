package mainapp; // Added package declaration

import exceptions.InsufficientFuelException;
import interfaces.CargoCarrier;
import interfaces.FuelConsumable;
import interfaces.Maintainable;
import interfaces.PassengerCarrier;
import manager.FleetManager;
import vehicles.*;
import exceptions.InvalidOperationException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        FleetManager manager = new FleetManager();


        Car car = new Car("W123", "Fortuner", 180, 4, 7);
        Truck truck = new Truck("W567", "TATA", 80, 10, 5000);
        Bus bus = new Bus("W673", "NDTL", 100, 6, 50, 500);
        Airplane airplane = new Airplane("W244", "Delhi", 800, 30000, 200, 10000);
        CargoShip ship = new CargoShip("W739", "Maersk", 30, false, 50000);

        try {
            manager.addVehicle(car);
            manager.addVehicle(truck);
            manager.addVehicle(bus);
            manager.addVehicle(airplane);
            manager.addVehicle(ship);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
        // System.out.println(manager.generateReport());
        // manager.saveToFile("fleet.csv");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n--- Fleet Management Menu ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Start Journey");
            System.out.println("4. Refuel All");
            System.out.println("5. Perform Maintenance");
            System.out.println("6. Generate Report");
            System.out.println("7. Save Fleet");
            System.out.println("8. Load Fleet");
            System.out.println("9. Search by Type");
            System.out.println("10. List Vehicles Needing Maintenance");

            // --- UPDATED MENU ---
            System.out.println("11. Sort Fleet by Speed");
            System.out.println("12. Sort Fleet by Model");
            System.out.println("13. Sort Fleet by Efficiency");
            System.out.println("14. Show Fastest & Slowest Vehicle");
            System.out.println("15. List Distinct Vehicle Models");
            System.out.println("16. Exit");


            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Enter vehicle type (Car, Truck, Bus, Airplane, CargoShip)");
                    String type = scanner.nextLine();
                    System.out.println("Enter ID:");
                    String id = scanner.nextLine();
                    System.out.println("Enter model:");
                    String model = scanner.nextLine();
                    System.out.println("Enter maxSpeed:");
                    double maxSpeed;
                    try {
                        maxSpeed = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid maxSpeed. Must be a number.");
                        scanner.nextLine();
                        continue;
                    }
                    Vehicle v = null;
                    if (type.equals("Car")) {
                        System.out.println("Enter numWheels:");
                        int numWheels;
                        try {
                            numWheels = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid numWheels. Must be an integer.");
                            scanner.nextLine();
                            continue;
                        }
                        System.out.println("Enter passengerCapacity:");
                        int pCap;
                        try {
                            pCap = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid passengerCapacity. Must be an integer.");
                            scanner.nextLine();
                            continue;
                        }
                        v = new Car(id, model, maxSpeed, numWheels, pCap);
                    } else if (type.equals("Truck")) {
                        System.out.println("Enter numWheels:");
                        int numWheels;
                        try {
                            numWheels = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid numWheels. Must be an integer.");
                            scanner.nextLine();
                            continue;
                        }
                        System.out.println("Enter cargoCapacity:");
                        double cCap;
                        try {
                            cCap = scanner.nextDouble();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid cargoCapacity. Must be a number.");
                            scanner.nextLine();
                            continue;
                        }
                        v = new Truck(id, model, maxSpeed, numWheels, cCap);
                    } else if (type.equals("Bus")) {
                        System.out.println("Enter numWheels:");
                        int numWheels;
                        try {
                            numWheels = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid numWheels. Must be an integer.");
                            scanner.nextLine();
                            continue;
                        }
                        System.out.println("Enter passengerCapacity:");
                        int pCap;
                        try {
                            pCap = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid passengerCapacity. Must be an integer.");
                            scanner.nextLine();
                            continue;
                        }
                        System.out.println("Enter cargoCapacity:");
                        double cCap;
                        try {
                            cCap = scanner.nextDouble();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid cargoCapacity. Must be a number.");
                            scanner.nextLine();
                            continue;
                        }
                        v = new Bus(id, model, maxSpeed, numWheels, pCap, cCap);
                    } else if (type.equals("Airplane")) {
                        System.out.println("Enter maxAltitude:");
                        double maxAltitude;
                        try {
                            maxAltitude = scanner.nextDouble();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid maxAltitude. Must be a number.");
                            scanner.nextLine();
                            continue;
                        }
                        System.out.println("Enter passengerCapacity:");
                        int pCap;
                        try {
                            pCap = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid passengerCapacity. Must be an integer.");
                            scanner.nextLine();
                            continue;
                        }
                        System.out.println("Enter cargoCapacity:");
                        double cCap;
                        try {
                            cCap = scanner.nextDouble();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid cargoCapacity. Must be a number.");
                            scanner.nextLine();
                            continue;
                        }
                        v = new Airplane(id, model, maxSpeed, maxAltitude, pCap, cCap);
                    } else if (type.equals("CargoShip")) {
                        System.out.println("Enter hasSail (true/false):");
                        boolean hasSail;
                        try {
                            hasSail = scanner.nextBoolean();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid hasSail. Must be true or false.");
                            scanner.nextLine();
                            continue;
                        }
                        System.out.println("Enter cargoCapacity:");
                        double cCap;
                        try {
                            cCap = scanner.nextDouble();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid cargoCapacity. Must be a number.");
                            scanner.nextLine();
                            continue;
                        }
                        v = new CargoShip(id, model, maxSpeed, hasSail, cCap);
                    }
                    if (v != null) {
                        System.out.println("Enter initial mileage:");
                        double initialMileage;
                        try {
                            initialMileage = scanner.nextDouble();
                            scanner.nextLine();
                            v.setCurrentMileage(initialMileage);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid mileage. Must be a number.");
                            scanner.nextLine();
                            break;
                        }

                        if (v instanceof FuelConsumable) {
                            System.out.println("Enter initial fuel level:");
                            double initialFuel;
                            try {
                                initialFuel = scanner.nextDouble();
                                scanner.nextLine();
                                ((FuelConsumable) v).refuel(initialFuel);
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid fuel level. Must be a number.");
                                scanner.nextLine();
                                break;
                            } catch (InvalidOperationException e) {
                                System.out.println("Error setting initial fuel: " + e.getMessage());
                                break;
                            }
                        }

                        try {
                            manager.addVehicle(v);
                            System.out.println(v.getClass().getSimpleName() + " added successfully.");
                        } catch (InvalidOperationException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid vehicle type");
                    }
                    break;
                case 2:

                    System.out.println("Enter ID to remove:");
                    String rid = scanner.nextLine();
                    try {
                        manager.removeVehicle(rid);
                        System.out.println("Vehicle " + rid + " removed.");
                    } catch (InvalidOperationException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Enter vehicle ID:");
                    String vid = scanner.nextLine();
                    Vehicle selectedVehicle = null;
                    List<Vehicle> allVehicles = manager.searchByType(Vehicle.class);
                    for (Vehicle veh : allVehicles) {
                        if (veh.getId().equals(vid)) {
                            selectedVehicle = veh;
                            break;
                        }
                    }
                    if (selectedVehicle == null) {
                        System.out.println("Vehicle not found");
                        break;
                    }
                    System.out.println("Enter distance (km):");
                    double dist;
                    try {
                        dist = scanner.nextDouble();
                        scanner.nextLine();
                        if (dist <= 0) {
                            System.out.println("Distance must be positive.");
                            continue;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid distance. Must be a number.");
                        scanner.nextLine();
                        continue;
                    }
                    if (selectedVehicle instanceof FuelConsumable fc) {
                        double efficiency = selectedVehicle.calculateFuelEfficiency();
                        if (efficiency <= 0) {
                            System.out.println("Vehicle cannot move due to invalid fuel efficiency.");
                            break;
                        }
                        double requiredFuel = dist / efficiency;
                        if (fc.getFuelLevel() < requiredFuel) {
                            System.out.println("Fuel is not enough. Required: " + requiredFuel + ", Available: " + fc.getFuelLevel());
                        } else {
                            try {
                                double beforeFuel = fc.getFuelLevel();
                                selectedVehicle.move(dist);
                                double afterFuel = fc.getFuelLevel();
                                double usedFuel = beforeFuel - afterFuel;
                                System.out.println("Journey completed. Fuel used: " + usedFuel + ", Remaining fuel: " + afterFuel);
                            } catch (InvalidOperationException | InsufficientFuelException e) {
                                System.out.println("Error during journey for " + selectedVehicle.getId() + ": " + e.getMessage());
                            }
                        }
                    } else {
                        try {
                            selectedVehicle.move(dist);
                            System.out.println("Journey completed (no fuel required)");
                        } catch (InvalidOperationException e) {
                            System.out.println("Error during journey for " + selectedVehicle.getId() + ": " + e.getMessage());
                        } catch (InsufficientFuelException e) {
                            throw new RuntimeException(e); // Should not happen for non-fuel
                        }
                    }
                    break;
                case 4:

                    System.out.println("Enter amount to refuel all:");
                    double amount;
                    try {
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                        if(amount <= 0) {
                            System.out.println("Amount must be positive.");
                            continue;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid amount. Must be a number.");
                        scanner.nextLine();
                        continue;
                    }
                    for (Vehicle veh : manager.searchByType(Vehicle.class)) {
                        if (veh instanceof FuelConsumable) {
                            try {
                                ((FuelConsumable) veh).refuel(amount);
                            } catch (InvalidOperationException e) {
                                System.out.println("Error refueling " + veh.getId() + ": " + e.getMessage());
                            }
                        }
                    }
                    System.out.println("Refuel complete for all applicable vehicles.");
                    break;
                case 5:

                    System.out.println("Performing maintenance on all vehicles that need it...");
                    manager.maintainAll();
                    System.out.println("Maintenance complete.");
                    break;
                case 6:

                    System.out.println(manager.generateReport());
                    break;
                case 7:

                    System.out.println("Enter filename to save:");
                    String file = scanner.nextLine();
                    manager.saveToFile(file);
                    System.out.println("Fleet saved to " + file);
                    break;
                case 8:

                    System.out.println("Enter filename to load:");
                    String loadFile = scanner.nextLine();
                    manager.loadFromFile(loadFile);

                    break;
                case 9:

                    System.out.println("Enter type (Car, Truck, Bus, Airplane, CargoShip, FuelConsumable, etc.):");
                    String stype = scanner.nextLine();
                    Class<?> clazz = null;
                    try {
                        clazz = switch (stype) {
                            case "FuelConsumable" -> FuelConsumable.class;
                            case "PassengerCarrier" -> PassengerCarrier.class;
                            case "CargoCarrier" -> CargoCarrier.class;
                            case "Maintainable" -> Maintainable.class;
                            default -> Class.forName("vehicles." + stype);
                        };
                    } catch (ClassNotFoundException e) {
                        System.out.println("Type not found");
                        break;
                    }
                    List<Vehicle> list = manager.searchByType(clazz);
                    if (list.isEmpty()) {
                        System.out.println("No vehicles found for type: " + stype);
                    } else {
                        System.out.println("--- Vehicles of type: " + stype + " ---");
                        for (Vehicle ve : list) {
                            ve.displayInfo();
                        }
                    }
                    break;
                case 10:

                    System.out.println("--- Vehicles Needing Maintenance ---");
                    List<Vehicle> maintList = manager.searchByType(Maintainable.class);
                    int count = 0;
                    for (Vehicle ve : maintList) {
                        if (((Maintainable) ve).needsMaintenance()) {
                            ve.displayInfo();
                            count++;
                        }
                    }
                    if (count == 0) {
                        System.out.println("No vehicles currently need maintenance.");
                    }
                    break;


                case 11:
                    manager.sortFleetBySpeed();
                    System.out.println("Fleet sorted by speed (slowest to fastest).");
                    break;

                case 12:
                    manager.sortFleetByModel();
                    System.out.println("Fleet sorted by model name (A-Z).");
                    break;

                case 13:
                    manager.sortFleetByEfficiency();
                    System.out.println("Fleet sorted by efficiency (least to most efficient).");
                    break;

                case 14:
                    System.out.println("--- Fleet Speed Extremes ---");
                    Vehicle fastest = manager.getFastestVehicle();
                    Vehicle slowest = manager.getSlowestVehicle();
                    if (fastest == null) { // Implies slowest is also null
                        System.out.println("Fleet is empty.");
                    } else {
                        System.out.print("Fastest: ");
                        fastest.displayInfo();
                        System.out.print("Slowest: ");
                        slowest.displayInfo();
                    }
                    break;

                case 15:
                    System.out.println("--- Distinct Vehicle Models (Alphabetical) ---");
                    Set<String> models = manager.getDistinctModelNames();
                    if (models.isEmpty()) {
                        System.out.println("No models in fleet.");
                    } else {
                        for (String m : models) {
                            System.out.println("- " + m);
                        }
                    }
                    break;

                case 16:
                    running = false;
                    break;


                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 16.");
            }
        }
        scanner.close();
        System.out.println("Exiting Fleet Management System. Goodbye!");
    }
}