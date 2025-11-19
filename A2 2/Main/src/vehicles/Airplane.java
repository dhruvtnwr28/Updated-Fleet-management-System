package vehicles;

import interfaces.FuelConsumable;
import interfaces.PassengerCarrier;
import interfaces.CargoCarrier;
import interfaces.Maintainable;
import exceptions.InvalidOperationException;
import exceptions.InsufficientFuelException;
import exceptions.OverloadException;

public class Airplane extends AirVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable {
    private double fuelLevel = 0;
    private final int passengerCapacity;
    private int currentPassengers = 0;
    private final double cargoCapacity;
    private double currentCargo = 0;
    private double lastMaintenanceMileage = 0;
    private boolean maintenanceNeeded = false;

    public Airplane(String id, String model, double maxSpeed, double maxAltitude, int passengerCapacity, double cargoCapacity) {
        super(id, model, maxSpeed, maxAltitude);
        this.passengerCapacity = passengerCapacity;
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance cannot be negative");
        }
        consumeFuel(distance);
        addMileage(distance);
        System.out.println("Flying at " + getMaxAltitude() + " ft for " + distance + " km.");
        if (getCurrentMileage() - lastMaintenanceMileage > 10000) {
            maintenanceNeeded = true;
        }
    }

    @Override
    public double calculateFuelEfficiency() {
        return 5.0;
    }

    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if (amount <= 0) {
            throw new InvalidOperationException("Amount must be positive");
        }
        fuelLevel += amount;
    }

    @Override
    public double getFuelLevel() {
        return fuelLevel;
    }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        double efficiency = calculateFuelEfficiency();
        double consumed = distance / efficiency;
        if (fuelLevel < consumed) {
            throw new InsufficientFuelException("Insufficient fuel");
        }
        fuelLevel -= consumed;
        return consumed;
    }

    @Override
    public void boardPassengers(int count) throws OverloadException, InvalidOperationException {
        if (count <= 0) {
            throw new InvalidOperationException("Count must be positive");
        }
        if (currentPassengers + count > passengerCapacity) {
            throw new OverloadException("Passenger overload");
        }
        currentPassengers += count;
    }

    @Override
    public void disembarkPassengers(int count) throws InvalidOperationException {
        if (count <= 0 || count > currentPassengers) {
            throw new InvalidOperationException("Invalid passenger count");
        }
        currentPassengers -= count;
    }

    @Override
    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    @Override
    public int getCurrentPassengers() {
        return currentPassengers;
    }

    @Override
    public void loadCargo(double weight) throws OverloadException, InvalidOperationException {
        if (weight <= 0) {
            throw new InvalidOperationException("Weight must be positive");
        }
        if (currentCargo + weight > cargoCapacity) {
            throw new OverloadException("Cargo overload");
        }
        currentCargo += weight;
    }

    @Override
    public void unloadCargo(double weight) throws InvalidOperationException {
        if (weight <= 0 || weight > currentCargo) {
            throw new InvalidOperationException("Invalid cargo weight");
        }
        currentCargo -= weight;
    }

    @Override
    public double getCargoCapacity() {
        return cargoCapacity;
    }

    @Override
    public double getCurrentCargo() {
        return currentCargo;
    }

    @Override
    public void scheduleMaintenance() {
        maintenanceNeeded = true;
    }

    @Override
    public boolean needsMaintenance() {
        return getCurrentMileage() - lastMaintenanceMileage > 10000 || maintenanceNeeded;
    }

    @Override
    public void performMaintenance() {
        lastMaintenanceMileage = getCurrentMileage();
        maintenanceNeeded = false;
        System.out.println("Maintenance performed for " + getId());
    }

    // Setters for loading
    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public void setCurrentPassengers(int currentPassengers) {
        this.currentPassengers = currentPassengers;
    }

    public void setCurrentCargo(double currentCargo) {
        this.currentCargo = currentCargo;
    }

    public void setLastMaintenanceMileage(double lastMaintenanceMileage) {
        this.lastMaintenanceMileage = lastMaintenanceMileage;
    }

    public void setMaintenanceNeeded(boolean maintenanceNeeded) {
        this.maintenanceNeeded = maintenanceNeeded;
    }

    public double getLastMaintenanceMileage() {
        return lastMaintenanceMileage;
    }

    public boolean isMaintenanceNeeded() {
        return maintenanceNeeded;
    }
}