package com.verbovskiy.server.model.service.impl;

import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.dao.OrderDao;
import com.verbovskiy.server.model.dao.impl.OrderDaoImpl;
import com.verbovskiy.server.model.entity.UserOrder;
import com.verbovskiy.server.model.service.OrderService;
import com.verbovskiy.server.util.date_converter.DateConverter;
import com.verbovskiy.server.validator.SearchValidator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Order service.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService {
    private final OrderDao dao = OrderDaoImpl.getInstance();

    @Override
    public void add(String userEmail, long carId, LocalDate date, boolean inProcessing) throws ServiceException {
        try {
            long dateLongFormat = DateConverter.convertToLong(date);
            dao.add(userEmail, carId, dateLongFormat, inProcessing);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void remove(long orderId) throws ServiceException {
        try {
            dao.remove(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public Optional<UserOrder> findByCarId(long carId) throws ServiceException {
        try {
            return dao.findByCarId(carId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<List<UserOrder>> findOrdersByParameters(String searchParameter, String brand, String color,
                                                            String boxType, String engineType) throws ServiceException {
        Optional<List<UserOrder>> orders = Optional.empty();

        if (SearchValidator.validateSearch(searchParameter)) {
            try {
               return Optional.of(dao.findBySearchParameters(searchParameter, brand, color, boxType, engineType));
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        return orders;
    }

    @Override
    public Optional<List<UserOrder>> findOrdersByUserEmail(String email) throws ServiceException {
        try {
            return Optional.of(dao.findByUserEmail(email));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateInProcessingStatus(long orderId, boolean status) throws ServiceException {
        try {
            dao.changeInProcessingStatus(orderId, status);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<UserOrder> findAllOrders() throws ServiceException {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Map<String, Double> calculateWeekProfit() throws ServiceException {
        try {
            Map<String, Double> profits= new HashMap<>();
            final int dayInWeekWithoutSunday = 6;
            double weekProfit = 0;
            LocalDate date = LocalDate.now();
            for (int i = 0; i < dayInWeekWithoutSunday; i++) {
                long dateLongFormat = DateConverter.convertToLong(date);
                double dayProfit = dao.calculateDateProfit(dateLongFormat);
                weekProfit += dayProfit;
                profits.put(RequestParameter.DAY_PROFIT + i, dayProfit);
                date = date.minusDays(1);
            }
            profits.put(RequestParameter.COMMON_COMPANY_PROFIT, weekProfit);
            return profits;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
