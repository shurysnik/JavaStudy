package org.example.service;


import org.example.Garage;
import org.example.model.Vehicle;

import java.time.LocalDateTime;
import java.util.Optional;

public class GarageService {
    private static GarageService instance;
    private Garage<Vehicle> vehicles;

    private GarageService() {
        vehicles = new Garage<>();
    }

    public static GarageService getInstance() {
        if (instance == null) {
            instance = new GarageService();
        }
        return instance;
    }

    public Optional<Vehicle> findByIndex(int index) {
        Vehicle foundVehicle = vehicles.get(index);
        return Optional.of(foundVehicle);
    }

    public Garage<Vehicle> getAll() {
        return vehicles;
    }

    public void add(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void insert(int index, Vehicle insertedVehicle) {
        vehicles.add(index, insertedVehicle);
    }

    public void set(int index, Vehicle vehicle) {
        vehicles.set(index, vehicle);
    }

    public boolean remove(int index) {
        if (index < 0 || index >= vehicles.getSize()) {
            return false;
        }
        vehicles.delete(index);
        return true;
    }

    public void print() {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    public LocalDateTime getCreatedDate(int index) {
        return vehicles.getFirstCreatedAt(index);
    }

    public LocalDateTime getUpdatedDate(int index) {
        return vehicles.getLastUpdateAt(index);
    }

    public int getRestylingAmount(int index) {
        return vehicles.getLastRestyling(index);
    }
}
