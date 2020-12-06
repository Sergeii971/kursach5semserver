package com.verbovskiy.server.model.dao;

import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.model.entity.Car;

import java.util.List;
import java.util.Optional;

/**
 * The interface Car dao.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public interface CarDao {
    /**
     * Add car
     *
     * @param brand the brand
     * @param price the price
     * @param description the description
     * @param imageName the imageName
     * @param isAvailable
     * @param addedDate the added date
     * @param model the model
     * @param manufactureYear the manufacture year
     * @param color the color
     * @param boxType the box type
     * @param engineType the engine type
     * @throws DaoException the dao exception
     */
    void add(String brand, String price, String description, String imageName,
             boolean isAvailable, long addedDate, String model, String manufactureYear,
             String color, String boxType, String engineType) throws DaoException;

    /**
     * Find all cars.
     *
     * @return the list of found cars
     * @throws DaoException the dao exception
     */
    List<Car> findAll() throws DaoException;

    /**
     * Remove car
     *
     * @param carId the car id
     * @throws DaoException the dao exception
     */
    void remove(long carId) throws DaoException;

    /**
     * Change car status.
     *
     * @param carId the car id
     * @param status car status
     * @throws DaoException the dao exception
     */
    void changeIsAvailableStatus(long carId, boolean status) throws DaoException;

    /**
     * Find all cars by search parameters for admin.
     *
     * @param searchParameter the search parameter
     * @param fromPrice from price
     * @param toPrice to price
     * @param brand the brand
     * @param boxType the box type
     * @param color the color
     * @param engineType the engine type
     * @return the list of found cars
     * @throws DaoException the dao exception
     */
    List<Car> adminFindBySearchParameters(String searchParameter, double fromPrice, double toPrice, String brand,
                                          String color, String boxType, String engineType) throws DaoException;

    /**
     * Find available cars.
     *
     * @return the list of found cars
     * @throws DaoException the dao exception
     */
    List<Car> findAvailableCar() throws DaoException;

    /**
     * Find all cars by search parameters for user.
     *
     * @param searchParameter the search parameter
     * @param fromPrice from price
     * @param toPrice to price
     * @param brand the brand
     * @param boxType the box type
     * @param color the color
     * @param engineType the engine type
     * @param isAvailable is available
     * @return the list of found cars
     * @throws DaoException the dao exception
     */
    List<Car> userFindBySearchParameters(String searchParameter, double fromPrice, double toPrice, String brand,
                                         String color, String boxType, String engineType, boolean isAvailable) throws DaoException;

    /**
     * Find car by id.
     *
     * @return optional
     * @throws DaoException the dao exception
     */
    Optional<String> findImageNameById(long carId) throws DaoException;

    /**
     * Find car by id.
     *
     * @return optional
     * @throws DaoException the dao exception
     */
    Optional<Car> findById(long carId) throws DaoException;
}
