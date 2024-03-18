package org.example.command;

import org.example.service.GarageService;

public class Print implements Command {
    private static final GarageService GARAGE_SERVICE = GarageService.getInstance();

    @Override
    public void execute() {
        GARAGE_SERVICE.print();
    }
}
