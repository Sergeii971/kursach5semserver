package com.verbovskiy.server.model.service;

import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.entity.Car;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Car service.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public interface CarService {
    /**
     * Add car
     *
     * @param brand the brand
     * @param price the price
     * @param description the description
     * @param imageName the imageName
     * @param isAvailable is available
     * @param addedDate the added date
     * @param model the model
     * @param manufactureYear the manufacture year
     * @param color the color
     * @param boxType the box type
     * @param engineType the engine type
     * @throws ServiceException the service exception
     */
    Map<String, Boolean> add(String brand, String price, String description, String imageName, boolean isAvailable,
                             LocalDate addedDate, String model, String manufactureYear,
                             String color, String boxType, String engineType) throws ServiceException;

    /**
     * Remove car
     *
     * @param carId the car id
     * @throws ServiceException the service exception
     */
    void remove(long carId) throws ServiceException;

    /**
     * Find car by id.
     *
     * @return optional
     * @throws ServiceException the service exception
     */
    Car findById(long carId) throws ServiceException;

    /**
     * Find all cars.
     *
     * @return the list of found cars
     * @throws ServiceException the service exception
     */
    List<Car> findAllCars() throws ServiceException;

    /**
     * Find all cars by search parameters.
     *
     * @param searchParameter the search parameter
     * @param fromPrice from price
     * @param toPrice to price
     * @param brand the brand
     * @param boxType the box type
     * @param color the color
     * @param engineType the engine type
     * @return the list of found cars
     * @throws ServiceException the service exception
     */
    Optional<List<Car>> findCarsByParameters(String searchParameter, String fromPrice, String toPrice, String brand,
                                             String color, String boxType, String engineType, boolean isAdmin) throws ServiceException;

    /**
     * Find available cars.
     *
     * @return the list of found cars
     * @throws ServiceException the service exception
     */
    List<Car> findAvailableCar() throws ServiceException;

    /**
     * Change car available status.
     *
     * @param carId the car id
     * @param status car status
     * @throws ServiceException the service exception
     */
    void updateIsAvailableStatus(long carId, boolean status) throws ServiceException;
}
