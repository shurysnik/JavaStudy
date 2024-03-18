package org.example.command;


import org.example.service.GarageService;
import org.example.util.UserInputUtil;

public class Insert implements Command {
    private static final GarageService GARAGE_SERVICE = GarageService.getInstance();

    @Override
    public void execute() {
        int index = UserInputUtil.getInt("Input index of added vehicle==>");
        GARAGE_SERVICE.insert(index, UserInputUtil.getVehicle());
    }
}
