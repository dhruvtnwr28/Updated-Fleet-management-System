package vehicles;

import exceptions.InvalidOperationException;
import exceptions.InsufficientFuelException;

public abstract class Vehicle implements Comparable<Vehicle> {
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage = 0;

    public Vehicle(String id, String model, double maxSpeed) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        this.id = id;
        this.model = model;
        this.maxSpeed = maxSpeed;
    }

    public abstract void move(double distance) throws InvalidOperationException, InsufficientFuelException;

    public abstract double calculateFuelEfficiency();

    public abstract double estimateJourneyTime(double distance);

    public void displayInfo() {
        System.out.println("ID: " + id + ", Model: " + model + ", Max Speed: " + maxSpeed + ", Mileage: " + currentMileage);
    }

    public double getCurrentMileage() {
        return currentMileage;
    }

    public String getId() {
        return id;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public String getModel() {
        return model;
    }

    protected void addMileage(double distance) {
        currentMileage += distance;
    }

    public void setCurrentMileage(double mileage) {
        this.currentMileage = mileage;
    }

    @Override
    public int compareTo(Vehicle other) {
        return Double.compare(this.calculateFuelEfficiency(), other.calculateFuelEfficiency());
    }
}