package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.entity.UserOrder;
import com.verbovskiy.server.model.service.OrderService;
import com.verbovskiy.server.model.service.impl.OrderServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteOrderCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(DeleteOrderCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        long orderId = Long.parseLong((String) request.get(RequestParameter.ORDER_ID));

        try {
            OrderService service = new OrderServiceImpl();
            service.remove(orderId);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return new HashMap<>();
    }
}
