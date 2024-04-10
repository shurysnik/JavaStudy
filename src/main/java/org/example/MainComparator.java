package org.example;

import org.example.model.Auto;
import org.example.model.Vehicle;
import org.example.service.AutoService;
import org.example.service.VehicleService;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MainComparator {

    public static void main(String[] args) {

        VehicleService<Auto> vehicleService = AutoService.getInstance();
        List<Auto> savedAutos = vehicleService.createAndSaveVehicles(500);
        Comparator<Auto> comparatorByPrice = Comparator.comparing(Vehicle::getPrice);
        Comparator<Auto> comparatorByModel = Comparator.comparing(Vehicle::getModel);
        Comparator<Auto> comparatorByCount = (o1, o2) -> Integer.compare(o2.getCount(), o1.getCount());
        Comparator<Auto> comparator = comparatorByPrice
                .reversed()
                .thenComparing(comparatorByModel)
                .thenComparing(comparatorByCount);
        Set<Auto> uniqueAutos = new TreeSet<>(comparator);
        uniqueAutos.addAll(savedAutos);



    }
}