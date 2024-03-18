package org.example.command;

import org.example.service.GarageService;
import org.example.util.UserInputUtil;

public class GetUpdatedLocalDate implements Command {
    private static final GarageService GARAGE_SERVICE = GarageService.getInstance();

    @Override
    public void execute() {
        int index = UserInputUtil.getInt("Input index of desired vehicle ==>");
        System.out.println(GARAGE_SERVICE.getUpdatedDate(index));
    }
}
