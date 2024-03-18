package org.example.command;

import org.example.model.Vehicle;
import org.example.service.GarageService;
import org.example.util.UserInputUtil;

import java.util.Optional;

public class Find implements Command {
    private static final GarageService GARAGE_SERVICE = GarageService.getInstance();

    @Override
    public void execute() {
        int index = UserInputUtil.getInt("Input index to find==>");
        Optional<Vehicle> foundIndex = GARAGE_SERVICE.findByIndex(index);
        foundIndex.ifPresentOrElse(vehicle -> System.out.println("vehicle=" + vehicle),
                () -> System.out.println("This " + index + "doesn't exist"));
    }
}
