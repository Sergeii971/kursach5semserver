package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.Constant;
import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.entity.Car;
import com.verbovskiy.server.model.service.CarService;
import com.verbovskiy.server.model.service.impl.CarServiceImpl;
import com.verbovskiy.server.util.byte_converter.ByteConverter;
import com.verbovskiy.server.util.file_image.FileImage;
import javafx.scene.image.Image;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowCarsCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(ShowCarsCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        CarService service = new CarServiceImpl();
        boolean isAdmin = (boolean) request.get(RequestParameter.IS_ADMIN);
        Map<String, Object> response = new HashMap<>();

        try {
            List<Car> allCars = isAdmin ? service.findAllCars() : service.findAvailableCar();
            response.put(RequestParameter.SIZE, allCars.size());
            for (int i = 0; i < allCars.size(); i++) {
                response.put(RequestParameter.CAR_ID + i, String.valueOf(allCars.get(i).getCarId()));
                response.put(RequestParameter.BRAND + i, allCars.get(i).getBrand().toString());
                response.put(RequestParameter.PRICE + i, String.valueOf(allCars.get(i).getPrice()));
                response.put(RequestParameter.DESCRIPTION + i, allCars.get(i).getDescription());
                response.put(RequestParameter.IS_AVAILABLE + i, allCars.get(i).getIsAvailable());
                response.put(RequestParameter.ADDED_DATE + i, allCars.get(i).getAddedDate());
                response.put(RequestParameter.MODEL + i, allCars.get(i).getModel());
                response.put(RequestParameter.MANUFACTURE_YEAR + i, allCars.get(i).getManufactureYear());
                response.put(RequestParameter.COLOR + i, allCars.get(i).getColor().toString());
                response.put(RequestParameter.ENGINE_TYPE + i, allCars.get(i).getEngineType().toString());
                response.put(RequestParameter.BOX_TYPE + i, allCars.get(i).getBoxType().toString());
                response.put(RequestParameter.IMAGE_NAME + i, allCars.get(i).getImageName());
                File file = new File(allCars.get(i).getImageName());
                response.put(RequestParameter.IMAGE + i, ByteConverter.convertToBytes(file));
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return response;
    }
}
