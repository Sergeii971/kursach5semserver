package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.AttributeKey;
import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.entity.Car;
import com.verbovskiy.server.model.service.CarService;
import com.verbovskiy.server.model.service.impl.CarServiceImpl;
import com.verbovskiy.server.util.byte_converter.ByteConverter;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FindCarsCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(FindCarsCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        String searchParameter = (String) request.get(RequestParameter.SEARCH_PARAMETER);
        String fromPrice = (String) request.get(RequestParameter.FROM_PRICE);
        String toPrice = (String) request.get(RequestParameter.TO_PRICE);
        String brand = (String) request.get(RequestParameter.BRAND);
        String color = (String) request.get(RequestParameter.COLOR);
        String boxType = (String) request.get(RequestParameter.BOX_TYPE);
        String engineType = (String) request.get(RequestParameter.ENGINE_TYPE);
        boolean isAdmin = (boolean) request.get(RequestParameter.IS_ADMIN);
        CarService service = new CarServiceImpl();
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<List<Car>> allCars = service.findCarsByParameters(searchParameter, fromPrice, toPrice,
                    brand, color, boxType, engineType, isAdmin);
            if (!allCars.isPresent()) {
                response.put(AttributeKey.INCORRECT_PARAMETER, true);
            } else {
                response.put(AttributeKey.INCORRECT_PARAMETER, false);
                response.put(RequestParameter.SIZE, allCars.get().size());
                for (int i = 0; i < allCars.get().size(); i++) {
                    response.put(RequestParameter.CAR_ID + i, String.valueOf(allCars.get().get(i).getCarId()));
                    response.put(RequestParameter.BRAND + i, allCars.get().get(i).getBrand().toString());
                    response.put(RequestParameter.PRICE + i, String.valueOf(allCars.get().get(i).getPrice()));
                    response.put(RequestParameter.DESCRIPTION + i, allCars.get().get(i).getDescription());
                    response.put(RequestParameter.IS_AVAILABLE + i, allCars.get().get(i).getIsAvailable());
                    response.put(RequestParameter.ADDED_DATE + i, allCars.get().get(i).getAddedDate());
                    response.put(RequestParameter.MODEL + i, allCars.get().get(i).getModel());
                    response.put(RequestParameter.MANUFACTURE_YEAR + i, allCars.get().get(i).getManufactureYear());
                    response.put(RequestParameter.COLOR + i, allCars.get().get(i).getColor().toString());
                    response.put(RequestParameter.ENGINE_TYPE + i, allCars.get().get(i).getEngineType().toString());
                    response.put(RequestParameter.BOX_TYPE + i, allCars.get().get(i).getBoxType().toString());
                    response.put(RequestParameter.IMAGE_NAME + i, allCars.get().get(i).getImageName());
                    File file = new File(allCars.get().get(i).getImageName());
                    response.put(RequestParameter.IMAGE + i, ByteConverter.convertToBytes(file));
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return response;
    }
}
