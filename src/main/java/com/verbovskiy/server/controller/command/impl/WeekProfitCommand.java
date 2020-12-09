package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.service.OrderService;
import com.verbovskiy.server.model.service.impl.OrderServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class WeekProfitCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(WeekProfitCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        OrderService service = new OrderServiceImpl();
        Map<String, Object> response = new HashMap<>();

        try {
            Map<String, Double> profits = service.calculateWeekProfit();
            response.put(RequestParameter.PROFITS, profits);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return response;
    }
}
