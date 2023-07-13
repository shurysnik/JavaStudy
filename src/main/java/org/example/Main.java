package org.example;

import org.example.model.Auto;
import org.example.model.CivilCar;
import org.example.model.SportCar;
import org.example.service.AutoService;
import org.example.service.CivilCarService;
import org.example.service.SportCarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final AutoService AUTO_SERVICE = new AutoService();
    private static final CivilCarService CIVIL_CAR_SERVICE = new CivilCarService();

    private static final SportCarService SPORT_CAR_SERVICE = new SportCarService();

    public static void main(String[] args) {

        List<Auto> autos = AUTO_SERVICE.createAuto(5);
        AUTO_SERVICE.saveAutos(autos);
        AUTO_SERVICE.printAll();
        LOGGER.info("-".repeat(170));

        List<CivilCar> civilCars = CIVIL_CAR_SERVICE.createAuto(5);
        CIVIL_CAR_SERVICE.saveAutos(civilCars);
        CIVIL_CAR_SERVICE.printAll();

        LOGGER.info("-".repeat(170));

        List<SportCar> sportCars = SPORT_CAR_SERVICE.createAuto(5);
        SPORT_CAR_SERVICE.saveAutos(sportCars);
        SPORT_CAR_SERVICE.printAll();
    }
}