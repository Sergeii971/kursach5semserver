package com.verbovskiy.server.model.service;

import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.entity.UserOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The interface Order service.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public interface OrderService {
    /**
     * Add order
     *
     * @param userEmail the user email
     * @param carId the car id
     * @param date the date
     * @param inProcessing is order in processing
     * @throws ServiceException the service exception
     */
    void add(String userEmail, long carId, LocalDate date, boolean inProcessing) throws ServiceException;

    /**
     * Remove order
     *
     * @param orderId the order id
     * @throws ServiceException the service exception
     */
    void remove(long orderId) throws ServiceException;

    /**
     * Find order by car id.
     *
     * @param carId the car id
     * @return optional
     * @throws ServiceException the service exception
     */
    Optional<UserOrder> findByCarId(long carId) throws ServiceException;

    /**
     * Find all cars by search parameters.
     *
     * @param searchParameter the search parameter
     * @param brand the brand
     * @param boxType the box type
     * @param color the color
     * @param engineType the engine type
     * @return the list of found orders
     * @throws ServiceException the service exception
     */
    Optional<List<UserOrder>> findOrdersByParameters(String searchParameter, String brand, String color,
                                                     String boxType, String engineType) throws ServiceException;

    /**
     * Find order by user email.
     *
     * @param email user email
     * @return the list of found orders
     * @throws ServiceException the service exception
     */
    Optional<List<UserOrder>> findOrdersByUserEmail(String email) throws ServiceException;

    /**
     * Change order status.
     *
     * @param orderId the order id
     * @param status order status
     * @throws ServiceException the service exception
     */
    void updateInProcessingStatus(long orderId, boolean status) throws ServiceException;

    /**
     * Find all orders.
     *
     * @return the list of found orders
     * @throws ServiceException the service exception
     */
    List<UserOrder> findAllOrders() throws ServiceException;
}
