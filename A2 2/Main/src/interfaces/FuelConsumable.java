package interfaces;

import exceptions.InvalidOperationException;
import exceptions.InsufficientFuelException;

public interface FuelConsumable {
    void refuel(double amount) throws InvalidOperationException;
    double getFuelLevel();  //current fuel value
    double consumeFuel(double distance) throws InsufficientFuelException;  // throws exception if not enough fuel
}