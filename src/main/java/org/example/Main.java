package org.example;

import org.example.model.Auto;
import org.example.reprository.AutoRepository;
import org.example.reprository.CivilCarRepository;
import org.example.reprository.SportCarRepository;
import org.example.service.AutoService;
import org.example.service.CivilCarService;
import org.example.service.SportCarService;

import java.util.List;


public class Main {

    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final CivilCarService CIVIL_CAR_SERVICE = new CivilCarService(new CivilCarRepository());

    private static final SportCarService SPORT_CAR_SERVICE = new SportCarService(new SportCarRepository());

    public static void main(String[] args) {

        List<Auto> autos = AUTO_SERVICE.createAndSaveAutos(5);
         AUTO_SERVICE.save(autos);
        AUTO_SERVICE.printAll();
        System.out.println("-".repeat(170));
    }
}