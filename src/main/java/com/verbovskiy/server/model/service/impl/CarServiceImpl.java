package com.verbovskiy.server.model.service.impl;

import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.dao.CarDao;
import com.verbovskiy.server.model.dao.impl.CarDaoImpl;
import com.verbovskiy.server.model.entity.Car;
import com.verbovskiy.server.model.service.CarService;
import com.verbovskiy.server.util.date_converter.DateConverter;
import com.verbovskiy.server.validator.CarValidator;
import com.verbovskiy.server.validator.SearchValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Car service.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class CarServiceImpl implements CarService {
    private static final String DEFAULT_IMAGE_NAME = "unknown.png";
    private static final String FOLDER_PATH = "C:\\Users\\sergei\\IdeaProjects\\epam.finalProject\\" +
            "target\\epam_finalProject-1.0-SNAPSHOT\\uploads\\";
    private final CarDao carDao = CarDaoImpl.getInstance();

    @Override
    public Map<String, Boolean> add(String brand, String price, String description, String imageName, boolean isAvailable,
                                    LocalDate addedDate, String model, String manufactureYear,
                                    String color, String boxType, String engineType) throws ServiceException {
        try {
            Map<String, Boolean> incorrectParameter = CarValidator.validateCarData(price, description, model, manufactureYear);
            if (incorrectParameter.size() == 0) {
                long dateLongFormat = DateConverter.convertToLong(addedDate);
                if ((imageName == null) || (imageName.isEmpty())) {
                    imageName = DEFAULT_IMAGE_NAME;
                }
                carDao.add(brand, price, description, imageName, isAvailable, dateLongFormat, model,
                        manufactureYear, color, boxType, engineType);
            }
            return incorrectParameter;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void remove(long carId) throws ServiceException {
        try {
            Optional<String> imageName = carDao.findImageNameById(carId);
            if (!imageName.isPresent()) {
                throw new ServiceException("error while find imageName");
            }
            String filePath = new StringBuilder().append(FOLDER_PATH).append(imageName.get()).toString();
            if (Files.exists(Paths.get(filePath)) && !imageName.get().equals(DEFAULT_IMAGE_NAME)) {
                Files.delete(Paths.get(filePath));
            }
            carDao.remove(carId);
        } catch (DaoException | IOException e) {
            throw new ServiceException("error while remove car", e);
        }

    }

    @Override
    public Car findById(long carId) throws ServiceException {
        try {
            Optional<Car> car = carDao.findById(carId);
            if (!car.isPresent()) {
                throw new ServiceException("error while find information about car by id");
            }
            return car.get();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Car> findAllCars() throws ServiceException {
        try {
            return carDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<List<Car>> findCarsByParameters(String searchParameter, String fromPrice, String toPrice, String brand,
                                                    String color, String boxType, String engineType, boolean isAdmin) throws ServiceException {
        Optional<List<Car>> cars = Optional.empty();

        if (SearchValidator.validateSearch(searchParameter) && SearchValidator.validatePrice(fromPrice)
                && SearchValidator.validatePrice(toPrice)) {
            try {
                double fromPriceDoubleFormat = fromPrice.isEmpty() ? 0 : Double.parseDouble(fromPrice);
                double toPriceDoubleFormat = toPrice.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(toPrice);
                if (isAdmin) {
                    cars = Optional.of(carDao.adminFindBySearchParameters(searchParameter, fromPriceDoubleFormat,
                            toPriceDoubleFormat, brand, color, boxType, engineType));
                } else {
                    boolean isAvailable = true;
                    cars = Optional.of(carDao.userFindBySearchParameters(searchParameter, fromPriceDoubleFormat,
                            toPriceDoubleFormat, brand, color, boxType, engineType, isAvailable));
                }
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        return cars;
    }

    @Override
    public List<Car> findAvailableCar() throws ServiceException {
        try {
            return carDao.findAvailableCar();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateIsAvailableStatus(long carId, boolean status) throws ServiceException {
        try {
            carDao.changeIsAvailableStatus(carId, status);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
