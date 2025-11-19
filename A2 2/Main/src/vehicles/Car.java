package vehicles;

import interfaces.FuelConsumable;
import interfaces.PassengerCarrier;
import interfaces.Maintainable;
import exceptions.InvalidOperationException;
import exceptions.InsufficientFuelException;
import exceptions.OverloadException;

public class Car extends LandVehicle implements FuelConsumable, Maintainable {
    private double fuelLevel = 0;
    private int passengerCapacity;
    private int currentPassengers = 0;
    private double lastMaintenanceMileage = 0;
    private boolean maintenanceNeeded = false;

    public Car(String id, String model, double maxSpeed, int numWheels, int passengerCapacity) {
        super(id, model, maxSpeed, numWheels);
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance cannot be negative");
        }
        consumeFuel(distance);
        addMileage(distance);
        System.out.println("Driving on road for " + distance + " km.");
        if (getCurrentMileage() - lastMaintenanceMileage > 10000) {
            maintenanceNeeded = true;
        }
    }

    @Override
    public double calculateFuelEfficiency() {
        return 15.0;
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