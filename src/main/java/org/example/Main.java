package org.example;

import org.example.model.Auto;
import org.example.service.AutoService;

import java.util.List;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService();

    public static void main(String[] args) {

        List<Auto> autos = AUTO_SERVICE.createAuto(5);
        AUTO_SERVICE.saveAutos(autos);
        AUTO_SERVICE.printAll();
    }
}