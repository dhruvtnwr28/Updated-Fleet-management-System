package vehicles;

import interfaces.FuelConsumable;
import interfaces.CargoCarrier;
import interfaces.Maintainable;
import exceptions.InvalidOperationException;
import exceptions.InsufficientFuelException;
import exceptions.OverloadException;

public class CargoShip extends WaterVehicle implements FuelConsumable, CargoCarrier, Maintainable {
    private double fuelLevel = 0;
    private double cargoCapacity;
    private double currentCargo = 0;
    private double lastMaintenanceMileage = 0;
    private boolean maintenanceNeeded = false;

    public CargoShip(String id, String model, double maxSpeed, boolean hasSail, double cargoCapacity) {
        super(id, model, maxSpeed, hasSail);
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance cannot be negative");
        }
        consumeFuel(distance);
        addMileage(distance);
        System.out.println("Sailing with cargo for " + distance + " km.");
        if (getCurrentMileage() - lastMaintenanceMileage > 10000) {
            maintenanceNeeded = true;
        }
    }

    @Override
    public double calculateFuelEfficiency() {
        return hasSail() ? 0 : 4.0;
    }

    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if (hasSail()) {
            throw new InvalidOperationException("This ship uses sail, no fuel needed");
        }
        if (amount <= 0) {
            throw new InvalidOperationException("Amount must be positive");
        }
        fuelLevel += amount;
    }

    @Override
    public double getFuelLevel() {
        return hasSail() ? 0 : fuelLevel;
    }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        if (hasSail()) {
            return 0;
        }
        double efficiency = calculateFuelEfficiency();
        double consumed = distance / efficiency;
        if (fuelLevel < consumed) {
            throw new InsufficientFuelException("Insufficient fuel");
        }
        fuelLevel -= consumed;
        return consumed;
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