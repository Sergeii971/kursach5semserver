package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.service.CarService;
import com.verbovskiy.server.model.service.impl.CarServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ChangeCarAvailableStatusCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(ChangeCarAvailableStatusCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        long carId = (long) request.get(RequestParameter.CAR_ID);
        boolean isAvailable = (boolean) request.get(RequestParameter.IS_AVAILABLE);

        try {
            CarService carService = new CarServiceImpl();
            carService.updateIsAvailableStatus(carId, isAvailable);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return new HashMap<>();
    }
}
