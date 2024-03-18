package org.example.util;

import org.example.model.Vehicle;
import org.example.model.VehicleType;
import org.example.service.AutoService;
import org.example.service.CivilCarService;
import org.example.service.SportCarService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInputUtil {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final SportCarService SPORT_CAR_SERVICE = SportCarService.getInstance();
    private static final AutoService AUTO_SERVICE = AutoService.getInstance();
    private static final CivilCarService CIVIL_CAR_SERVICE = CivilCarService.getInstance();

    private UserInputUtil() {

    }

    public static int getInt(String message) {
        System.out.println(message);
        return SCANNER.nextInt();
    }

    public static Vehicle getVehicle() {
        final VehicleType[] values = VehicleType.values();
        List<String> names = getNamesVehicleType(values);
        int userInput = UserInputUtil.getUserInput("What do you want to create :", names);

        return switch (values[userInput]) {
            case AUTO -> AUTO_SERVICE.create();
            case SPORT -> SPORT_CAR_SERVICE.create();
            case CIVIL -> CIVIL_CAR_SERVICE.create();

        };
    }

    public static int getUserInput(String message, List<String> names) {
        int userInput;
        do {
            System.out.println(message);
            for (int i = 0; i < names.size(); i++) {
                System.out.printf("%d) %s%n", i, names.get(i));
            }
            userInput = SCANNER.nextInt();
        }
        while (userInput < 0 || userInput >= names.size());
        return userInput;
    }

    private static List<String> getNamesVehicleType(VehicleType[] values) {
        final List<String> names = new ArrayList<>(values.length);
        for (VehicleType type : values) {
            names.add(type.name());
        }
        return names;
    }
}
