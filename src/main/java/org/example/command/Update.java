package org.example.command;


import org.example.service.GarageService;
import org.example.util.UserInputUtil;

public class Update implements Command {
    private static final GarageService GARAGE_SERVICE = GarageService.getInstance();

    @Override
    public void execute() {
        int index = UserInputUtil.getInt("Input index to update vehicle ==> ");
        GARAGE_SERVICE.set(index, UserInputUtil.getVehicle());
    }
}

