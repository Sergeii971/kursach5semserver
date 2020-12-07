package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.AttributeKey;
import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.service.CarService;
import com.verbovskiy.server.model.service.impl.CarServiceImpl;
import com.verbovskiy.server.util.file_image.FileImage;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AddCarCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(AddCarCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        CarService service = new CarServiceImpl();
        String brand = (String) request.get(RequestParameter.BRAND);
        String price = (String) request.get(RequestParameter.PRICE);
        String description = (String) request.get(RequestParameter.DESCRIPTION);
        String model = (String) request.get(RequestParameter.MODEL);
        String manufactureYear = (String) request.get(RequestParameter.MANUFACTURE_YEAR);
        String color = (String) request.get(RequestParameter.COLOR);
        String engineType = (String) request.get(RequestParameter.ENGINE_TYPE);
        String boxType = (String) request.get(RequestParameter.BOX_TYPE);
        boolean isAvailable = true;
        LocalDate addedDate = LocalDate.now();
        byte[] bytes = (byte[]) request.get(RequestParameter.IMAGE);
        String randFilename = FileImage.saveImage(bytes);
        Map<String, Object> response = new HashMap<>();

        try {
            Map<String, Boolean> incorrectParameter = service.add(brand, price, description, randFilename, isAvailable,
                    addedDate, model, manufactureYear, color, boxType, engineType);
            response.put(AttributeKey.INCORRECT_PARAMETER, incorrectParameter);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return response;
    }
}
