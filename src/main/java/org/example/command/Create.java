package org.example.command;

import org.example.service.GarageService;
import org.example.util.UserInputUtil;


public class Create implements Command {
    private static final GarageService GARAGE_SERVICE = GarageService.getInstance();

    @Override
    public void execute() {
        GARAGE_SERVICE.add(UserInputUtil.getVehicle());
    }

}
