package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.AttributeKey;
import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.entity.Car;
import com.verbovskiy.server.model.entity.UserOrder;
import com.verbovskiy.server.model.service.CarService;
import com.verbovskiy.server.model.service.OrderService;
import com.verbovskiy.server.model.service.impl.CarServiceImpl;
import com.verbovskiy.server.model.service.impl.OrderServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

public class DeleteCarCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(DeleteCarCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        long carId = (long) request.get(RequestParameter.CAR_ID);
        Map<String, Object> response = new HashMap<>();

        try {
            OrderService orderService = new OrderServiceImpl();
            Optional<UserOrder> order =orderService.findByCarId(carId);
            if (order.isPresent()) {
                response.put(RequestParameter.IN_ORDER_LIST, true);
            } else {
                response.put(RequestParameter.IN_ORDER_LIST, false);
                CarService service = new CarServiceImpl();
                service.remove(carId);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return response;
    }
}
