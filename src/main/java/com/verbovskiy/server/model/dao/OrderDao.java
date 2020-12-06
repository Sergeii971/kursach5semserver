package com.verbovskiy.server.model.dao;

import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.model.entity.UserOrder;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order dao.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public interface OrderDao {
    /**
     * Add order
     *
     * @param userEmail the user email
     * @param carId the car id
     * @param date the date
     * @param inProcessing is order in processing
     * @throws DaoException the dao exception
     */
    void add(String userEmail, long carId, long date, boolean inProcessing) throws DaoException;

    /**
     * Remove order
     *
     * @param orderId the order id
     * @throws DaoException the dao exception
     */
    void remove(long orderId) throws DaoException;

    /**
     * Find all cars by search parameters for admin.
     *
     * @param searchParameter the search parameter
     * @param brand the brand
     * @param boxType the box type
     * @param color the color
     * @param engineType the engine type
     * @return the list of found orders
     * @throws DaoException the dao exception
     */
    List<UserOrder> findBySearchParameters(String searchParameter, String brand, String color,
                                           String boxType, String engineType) throws DaoException;

    /**
     * Change order status.
     *
     * @param orderId the order id
     * @param status order status
     * @throws DaoException the dao exception
     */
    void changeInProcessingStatus(long orderId, boolean status) throws DaoException;

    /**
     * Find all orders.
     *
     * @return the list of found orders
     * @throws DaoException the dao exception
     */
    List<UserOrder> findAll() throws DaoException;

    /**
     * Find order by user email.
     *
     * @param email user email
     * @return the list of found orders
     * @throws DaoException the dao exception
     */
    List<UserOrder> findByUserEmail(String email) throws DaoException;

    /**
     * Find order by car id.
     *
     * @param carId the car id
     * @return optional
     * @throws DaoException the dao exception
     */
    Optional<UserOrder> findByCarId(long carId) throws DaoException;
}
