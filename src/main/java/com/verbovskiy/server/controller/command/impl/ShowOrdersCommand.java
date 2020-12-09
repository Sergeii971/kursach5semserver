package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.entity.UserOrder;
import com.verbovskiy.server.model.service.OrderService;
import com.verbovskiy.server.model.service.impl.OrderServiceImpl;
import com.verbovskiy.server.util.byte_converter.ByteConverter;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowOrdersCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(ShowOrdersCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        OrderService service = new OrderServiceImpl();
        Map<String, Object> response = new HashMap<>();

        try {
            List<UserOrder> allOrders = service.findAllOrders();
            response.put(RequestParameter.SIZE, allOrders.size());
            for (int i = 0; i < allOrders.size(); i++) {
                response.put(RequestParameter.ORDER_ID + i, String.valueOf(allOrders.get(i).getOrderId()));
                response.put(RequestParameter.ORDER_DATE + i, allOrders.get(i).getDate());
                response.put(RequestParameter.IN_PROCESSING + i, allOrders.get(i).isInProcessing());
                response.put(RequestParameter.CAR_ID + i, String.valueOf(allOrders.get(i).getCar().getCarId()));
                response.put(RequestParameter.BRAND + i, allOrders.get(i).getCar().getBrand().toString());
                response.put(RequestParameter.PRICE + i, String.valueOf(allOrders.get(i).getCar().getPrice()));
                response.put(RequestParameter.DESCRIPTION + i, allOrders.get(i).getCar().getDescription());
                response.put(RequestParameter.IS_AVAILABLE + i, allOrders.get(i).getCar().getIsAvailable());
                response.put(RequestParameter.ADDED_DATE + i, allOrders.get(i).getCar().getAddedDate());
                response.put(RequestParameter.MODEL + i, allOrders.get(i).getCar().getModel());
                response.put(RequestParameter.MANUFACTURE_YEAR + i, allOrders.get(i).getCar().getManufactureYear());
                response.put(RequestParameter.COLOR + i, allOrders.get(i).getCar().getColor().toString());
                response.put(RequestParameter.ENGINE_TYPE + i, allOrders.get(i).getCar().getEngineType().toString());
                response.put(RequestParameter.BOX_TYPE + i, allOrders.get(i).getCar().getBoxType().toString());
                response.put(RequestParameter.IMAGE_NAME + i, allOrders.get(i).getCar().getImageName());
                File file = new File(allOrders.get(i).getCar().getImageName());
                response.put(RequestParameter.IMAGE + i, ByteConverter.convertToBytes(file));
                response.put(RequestParameter.LOGIN + i, allOrders.get(i).getUser().getEmail());
                response.put(RequestParameter.NAME + i, allOrders.get(i).getUser().getName());
                response.put(RequestParameter.SURNAME + i, allOrders.get(i).getUser().getSurname());
                response.put(RequestParameter.IS_BLOCKED + i, allOrders.get(i).getUser().getAccount().isBlocked());
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return response;
    }
}
