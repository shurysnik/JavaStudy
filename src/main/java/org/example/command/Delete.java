package org.example.command;

import org.example.service.GarageService;
import org.example.util.UserInputUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Delete implements Command {
    private static final GarageService GARAGE_SERVICE = GarageService.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(Delete.class);

    @Override()
    public void execute() {
        int index = UserInputUtil.getInt("Input index to remove==>");
        boolean isRemoved = GARAGE_SERVICE.remove(index);
        if (isRemoved) {
            LOGGER.info("Vehicle was successfully removed by index = {}", index);
        }
    }


}

