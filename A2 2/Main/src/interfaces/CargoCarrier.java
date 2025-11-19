package interfaces;

import exceptions.OverloadException;
import exceptions.InvalidOperationException;

public interface CargoCarrier {
    void loadCargo(double weight) throws OverloadException, InvalidOperationException;
    void unloadCargo(double weight) throws InvalidOperationException;
    double getCargoCapacity(); //max capacity
    double getCurrentCargo(); //current cargo
}