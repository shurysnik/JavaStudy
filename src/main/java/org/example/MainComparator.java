package org.example;

import org.example.model.Auto;
import org.example.model.Vehicle;
import org.example.reprository.AutoRepository;
import org.example.service.AutoService;
import org.example.service.VehicleService;

import java.util.*;

public class MainComparator {

    public static void main(String[] args) {

        AutoRepository autoRepository = new AutoRepository();
        VehicleService<Auto> vehicleService = new AutoService(autoRepository);
        List<Auto> savedAutos = vehicleService.createAndSaveAutos(500);
        Comparator<Auto> comparatorByPrice = Comparator.comparing(Vehicle::getPrice);
        Comparator<Auto> comparatorByModel = Comparator.comparing(Vehicle::getModel);
        Comparator<Auto> comparatorByCount = (o1, o2) -> Integer.compare(o2.getCount(), o1.getCount());
        Comparator<Auto> comparator = comparatorByPrice
                .reversed()
                .thenComparing(comparatorByModel)
                .thenComparing(comparatorByCount);
        Set<Auto> uniqueAutos = new TreeSet<>(comparator);
        uniqueAutos.addAll(savedAutos);
        for (Auto auto : uniqueAutos) {
            System.out.println(auto);
        }
    }
}