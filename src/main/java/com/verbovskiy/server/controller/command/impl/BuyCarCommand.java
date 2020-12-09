package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.service.OrderService;
import com.verbovskiy.server.model.service.impl.OrderServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BuyCarCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(BuyCarCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        long carId = (long) request.get(RequestParameter.CAR_ID);
        String userEmail = (String) request.get(RequestParameter.EMAIL);
        LocalDate date = LocalDate.now();
        boolean inProcessing = true;
        Map<String, Object> response = new HashMap<>();

        try {
            OrderService orderService = new OrderServiceImpl();
            orderService.add(userEmail, carId, date, inProcessing);
            response.put(RequestParameter.IS_BOUGHT, true);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return response;
    }
}
