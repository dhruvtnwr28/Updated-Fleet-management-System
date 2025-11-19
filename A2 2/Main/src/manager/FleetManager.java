package manager;

import interfaces.CargoCarrier;
import interfaces.PassengerCarrier;
import vehicles.*;
import interfaces.FuelConsumable;
import interfaces.Maintainable;
import exceptions.InvalidOperationException;
import exceptions.InsufficientFuelException;
import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FleetManager {
    private List<Vehicle> fleet = new ArrayList<>();

    public void addVehicle(Vehicle v) throws InvalidOperationException {
        for (Vehicle veh : fleet) {
            if (veh.getId().equals(v.getId())) {
                throw new InvalidOperationException("Duplicate ID");
            }
        }
        fleet.add(v);
    }

    public void removeVehicle(String id) throws InvalidOperationException {
        Vehicle toRemove = null;
        for (Vehicle v : fleet) {
            if (v.getId().equals(id)) {
                toRemove = v;
                break;
            }
        }
        if (toRemove == null) {
            throw new InvalidOperationException("Vehicle not found");
        }
        fleet.remove(toRemove);
    }

    public void startAllJourneys(double distance) {
        for (Vehicle v : fleet) {
            try {
                v.move(distance);
            } catch (Exception e) {
                System.out.println("Error for " + v.getId() + ": " + e.getMessage());
            }
        }
    }

    public double getTotalFuelConsumption(double distance) {
        double total = 0;
        for (Vehicle v : fleet) {
            if (v instanceof FuelConsumable) {
                try {
                    total += ((FuelConsumable) v).consumeFuel(distance);
                } catch (InsufficientFuelException e) {
                    System.out.println("Insufficient fuel for " + v.getId());
                }
            }
        }
        return total;
    }

    public void maintainAll() {
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable && ((Maintainable) v).needsMaintenance()) {
                ((Maintainable) v).performMaintenance();
            }
        }
    }

    public List<Vehicle> searchByType(Class<?> type) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : fleet) {
            if (type.isInstance(v)) {
                result.add(v);
            }
        }
        return result;
    }

    public void sortFleetByEfficiency() {
        Collections.sort(fleet);
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("--- Fleet Management Report ---").append("\n");
        report.append("Total vehicles: ").append(fleet.size()).append("\n");
        report.append("\n--- Vehicle Counts ---").append("\n");
        report.append("Cars: ").append(searchByType(Car.class).size()).append("\n");
        report.append("Trucks: ").append(searchByType(Truck.class).size()).append("\n");
        report.append("Buses: ").append(searchByType(Bus.class).size()).append("\n");
        report.append("Airplanes: ").append(searchByType(Airplane.class).size()).append("\n");
        report.append("CargoShips: ").append(searchByType(CargoShip.class).size()).append("\n");

        report.append("\n--- Performance Stats ---").append("\n");
        double totalEfficiency = 0;
        double totalMileage = 0;
        int needingMaintenance = 0;
        for (Vehicle v : fleet) {
            totalEfficiency += v.calculateFuelEfficiency();
            totalMileage += v.getCurrentMileage();
            if (v instanceof Maintainable && ((Maintainable) v).needsMaintenance()) {
                needingMaintenance++;
            }
        }
        double avgEfficiency = fleet.isEmpty() ? 0 : totalEfficiency / fleet.size();
        report.append(String.format("Average efficiency: %.2f km/l\n", avgEfficiency));
        report.append(String.format("Total mileage: %.2f km\n", totalMileage));
        report.append("Vehicles needing maintenance: ").append(needingMaintenance).append("\n");

        report.append("\n--- Full Fleet List ---").append("\n");
        if (fleet.isEmpty()) {
            report.append("No vehicles in fleet.").append("\n");
        } else {
            for (Vehicle v : fleet) {
                report.append(String.format("- %s (ID: %s, Model: %s, Mileage: %.2f, MaxSpeed: %.1f)",
                        v.getClass().getSimpleName(),
                        v.getId(),
                        v.getModel(),
                        v.getCurrentMileage(),
                        v.getMaxSpeed()));
                report.append("\n");
            }
        }

        report.append("---------------------------------").append("\n");
        return report.toString();
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            for (Vehicle v : fleet) {
                String type = v.getClass().getSimpleName();
                writer.print(type + "," + v.getId() + "," + v.getModel() + "," + v.getMaxSpeed() + ",");
                if (v instanceof LandVehicle) {
                    writer.print(((LandVehicle) v).getNumWheels() + ",");
                } else if (v instanceof AirVehicle) {
                    writer.print(((AirVehicle) v).getMaxAltitude() + ",");
                } else if (v instanceof WaterVehicle) {
                    writer.print(((WaterVehicle) v).hasSail() + ",");
                }
                if (v instanceof FuelConsumable) {
                    writer.print(((FuelConsumable) v).getFuelLevel() + ",");
                } else {
                    writer.print("0,");
                }
                if (v instanceof PassengerCarrier) {
                    writer.print(((PassengerCarrier) v).getPassengerCapacity() + "," + ((PassengerCarrier) v).getCurrentPassengers() + ",");
                } else {
                    writer.print("0,0,");
                }
                if (v instanceof CargoCarrier) {
                    writer.print(((CargoCarrier) v).getCargoCapacity() + "," + ((CargoCarrier) v).getCurrentCargo() + ",");
                } else {
                    writer.print("0,0,");
                }
                writer.print(v.getCurrentMileage() + ",");
                if (v instanceof Maintainable) {
                    if (v instanceof Car) {
                        writer.print(((Car) v).getLastMaintenanceMileage() + "," + ((Car) v).isMaintenanceNeeded());
                    } else if (v instanceof Truck) {
                        writer.print(((Truck) v).getLastMaintenanceMileage() + "," + ((Truck) v).isMaintenanceNeeded());
                    } else if (v instanceof Bus) {
                        writer.print(((Bus) v).getLastMaintenanceMileage() + "," + ((Bus) v).isMaintenanceNeeded());
                    } else if (v instanceof Airplane) {
                        writer.print(((Airplane) v).getLastMaintenanceMileage() + "," + ((Airplane) v).isMaintenanceNeeded());
                    } else if (v instanceof CargoShip) {
                        writer.print(((CargoShip) v).getLastMaintenanceMileage() + "," + ((CargoShip) v).isMaintenanceNeeded());
                    }
                } else {
                    writer.print("0,false");
                }
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        fleet.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] parts = line.split(",");
                    if (parts.length < 13) {
                        System.out.println("Skipping malformed line (not enough data): " + line);
                        continue;
                    }

                    String type = parts[0];
                    String id = parts[1];
                    String model = parts[2];
                    double maxSpeed = Double.parseDouble(parts[3]);

                    Vehicle v = null;

                    double fuelLevel = Double.parseDouble(parts[5]);
                    double currentMileage = Double.parseDouble(parts[10]);
                    double lastMaintMileage = Double.parseDouble(parts[11]);
                    boolean needsMaint = Boolean.parseBoolean(parts[12]);

                    switch (type) {
                        case "Car":
                            int numWheels = Integer.parseInt(parts[4]);
                            int pCap = Integer.parseInt(parts[6]);
                            int cPass = Integer.parseInt(parts[7]);

                            Car car = new Car(id, model, maxSpeed, numWheels, pCap);
                            car.setCurrentPassengers(cPass);
                            car.setFuelLevel(fuelLevel);
                            car.setCurrentMileage(currentMileage);
                            car.setLastMaintenanceMileage(lastMaintMileage);
                            car.setMaintenanceNeeded(needsMaint);
                            v = car;
                            break;

                        case "Truck":
                            numWheels = Integer.parseInt(parts[4]);
                            double cCap = Double.parseDouble(parts[8]);
                            double cCargo = Double.parseDouble(parts[9]);

                            Truck truck = new Truck(id, model, maxSpeed, numWheels, cCap);
                            truck.setCurrentCargo(cCargo);
                            truck.setFuelLevel(fuelLevel);
                            truck.setCurrentMileage(currentMileage);
                            truck.setLastMaintenanceMileage(lastMaintMileage);
                            truck.setMaintenanceNeeded(needsMaint);
                            v = truck;
                            break;

                        case "Bus":
                            numWheels = Integer.parseInt(parts[4]);
                            pCap = Integer.parseInt(parts[6]);
                            cPass = Integer.parseInt(parts[7]);
                            cCap = Double.parseDouble(parts[8]);
                            cCargo = Double.parseDouble(parts[9]);

                            Bus bus = new Bus(id, model, maxSpeed, numWheels, pCap, cCap);
                            bus.setCurrentPassengers(cPass);
                            bus.setCurrentCargo(cCargo);
                            bus.setFuelLevel(fuelLevel);
                            bus.setCurrentMileage(currentMileage);
                            bus.setLastMaintenanceMileage(lastMaintMileage);
                            bus.setMaintenanceNeeded(needsMaint);
                            v = bus;
                            break;

                        case "Airplane":
                            double maxAltitude = Double.parseDouble(parts[4]);
                            pCap = Integer.parseInt(parts[6]);
                            cPass = Integer.parseInt(parts[7]);
                            cCap = Double.parseDouble(parts[8]);
                            cCargo = Double.parseDouble(parts[9]);

                            Airplane airplane = new Airplane(id, model, maxSpeed, maxAltitude, pCap, cCap);
                            airplane.setCurrentPassengers(cPass);
                            airplane.setCurrentCargo(cCargo);
                            airplane.setFuelLevel(fuelLevel);
                            airplane.setCurrentMileage(currentMileage);
                            airplane.setLastMaintenanceMileage(lastMaintMileage);
                            airplane.setMaintenanceNeeded(needsMaint);
                            v = airplane;
                            break;

                        case "CargoShip":
                            boolean hasSail = Boolean.parseBoolean(parts[4]);
                            cCap = Double.parseDouble(parts[8]);
                            cCargo = Double.parseDouble(parts[9]);

                            CargoShip ship = new CargoShip(id, model, maxSpeed, hasSail, cCap);
                            ship.setCurrentCargo(cCargo);
                            ship.setFuelLevel(fuelLevel);
                            ship.setCurrentMileage(currentMileage);
                            ship.setLastMaintenanceMileage(lastMaintMileage);
                            ship.setMaintenanceNeeded(needsMaint);
                            v = ship;
                            break;

                        default:
                            System.out.println("Skipping unknown vehicle type: " + type);
                    }

                    if (v != null) {
                        this.addVehicle(v);
                    }

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException | InvalidOperationException e) {
                    System.out.println("Skipping malformed or duplicate line: " + line + " (Error: " + e.getMessage() + ")");
                }
            }
            System.out.println("Fleet successfully loaded from " + filename);

        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private Comparator<Vehicle> getComparatorBySpeed() {
        return new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                return Double.compare(v1.getMaxSpeed(), v2.getMaxSpeed());
            }
        };
    }

    private Comparator<Vehicle> getComparatorByModel() {
        return new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                return v1.getModel().compareTo(v2.getModel());
            }
        };
    }

    public void sortFleetBySpeed() {
        Collections.sort(fleet, getComparatorBySpeed());
    }

    public void sortFleetByModel() {
        Collections.sort(fleet, getComparatorByModel());
    }

    public Vehicle getFastestVehicle() {
        if (fleet.isEmpty()) {
            return null;
        }
        return Collections.max(fleet, getComparatorBySpeed());
    }

    public Vehicle getSlowestVehicle() {
        if (fleet.isEmpty()) {
            return null;
        }
        return Collections.min(fleet, getComparatorBySpeed());
    }

    public Set<String> getDistinctModelNames() {
        Set<String> modelNames = new TreeSet<>();
        for (Vehicle v : fleet) {
            modelNames.add(v.getModel());
        }
        return modelNames;

    }
}