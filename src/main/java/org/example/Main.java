package org.example;

import org.example.model.*;
import org.example.service.AutoService;
import org.example.service.CivilCarService;
import org.example.service.SportCarService;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    private static final AutoService AUTO_SERVICE = new AutoService();
    private static final CivilCarService CIVIL_CAR_SERVICE = new CivilCarService();

    private static final SportCarService SPORT_CAR_SERVICE = new SportCarService();

    public static void main(String[] args) {


        System.out.println("-".repeat(60) + "update" + "-".repeat(60));
        Auto auto = new Auto("Model-" + 0, BigDecimal.ZERO, Manufacturer.HYUNDAI, RacingTires.RACING, "Model-" + 0);
        AUTO_SERVICE.saveAuto(auto);
        Auto newAutoUpdate = AUTO_SERVICE.update(auto);
        AUTO_SERVICE.saveAuto(newAutoUpdate);

        AUTO_SERVICE.printAll();
        AUTO_SERVICE.delete(auto.getId());
        AUTO_SERVICE.delete(newAutoUpdate.getId());

        System.out.println("-".repeat(60) + "delete" + "-".repeat(60));
        AUTO_SERVICE.saveAuto(auto);
        AUTO_SERVICE.printAll();

        boolean isDeleted = AUTO_SERVICE.delete(auto.getId());
        System.out.println("Delete by object: " + isDeleted);
        AUTO_SERVICE.printAll();

        System.out.println("-".repeat(170));

        List<Auto> autos = AUTO_SERVICE.createAuto(5);
        AUTO_SERVICE.saveAutos(autos);
        AUTO_SERVICE.printAll();
        System.out.println("-".repeat(170));

        List<CivilCar> civilCars = CIVIL_CAR_SERVICE.createAuto(5);
        CIVIL_CAR_SERVICE.saveAutos(civilCars);
        CIVIL_CAR_SERVICE.printAll();

        System.out.println("-".repeat(170));

        List<SportCar> sportCars = SPORT_CAR_SERVICE.createAuto(5);
        SPORT_CAR_SERVICE.saveAutos(sportCars);
        SPORT_CAR_SERVICE.printAll();
    }
}