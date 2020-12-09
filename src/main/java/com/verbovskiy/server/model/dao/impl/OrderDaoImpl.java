package com.verbovskiy.server.model.dao.impl;

import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.model.connection.ConnectionPool;
import com.verbovskiy.server.model.dao.ColumnName;
import com.verbovskiy.server.model.dao.OrderDao;
import com.verbovskiy.server.model.dao.query.DatabaseQuery;
import com.verbovskiy.server.model.entity.*;
import com.verbovskiy.server.util.date_converter.DateConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Order dao.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class OrderDaoImpl implements OrderDao {
    private static OrderDao instance;

    private OrderDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static OrderDao getInstance() {
        if (instance == null) {
            instance = new OrderDaoImpl();
        }
        return instance;
    }

    @Override
    public void add(String userEmail, long carId, long date, boolean inProcessing) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.ADD_ORDER)) {
            statement.setLong(1, date);
            statement.setString(2, userEmail);
            statement.setLong(3, carId);
            statement.setBoolean(4, inProcessing);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while adding order to database", e);
        }
    }

    @Override
    public void remove(long orderId) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement accountStatement = connection.prepareStatement(DatabaseQuery.REMOVE_ORDER)) {
            accountStatement.setLong(1, orderId);
            accountStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error while removing order from database", e);
        }
    }

    @Override
    public List<UserOrder> findBySearchParameters(String searchParameter, String brand, String color,
                                                  String boxType, String engineType) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.FIND_ORDERS_BY_SEARCH_PARAMETERS)) {
            statement.setString(1, brand);
            statement.setString( 2, color);
            statement.setString(3, engineType);
            statement.setString(4, boxType);
            statement.setString(5, searchParameter);
            List<UserOrder> orders = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserOrder order = createOrderFromSql(resultSet);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException("Error while finding orders by search parameters in database", e);
        }
    }

    @Override
    public void changeInProcessingStatus(long orderId, boolean status) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.CHANGE_IS_PROCESSING_ORDER_STATUS)) {
            statement.setBoolean(1, status);
            statement.setLong(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error during changing order status in database", e);
        }
    }

    @Override
    public List<UserOrder> findAll() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.FIND_ALL_ORDERS)) {
            List<UserOrder> orders = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserOrder order = createOrderFromSql(resultSet);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException("Error while get all orders from database", e);
        }
    }

    @Override
    public List<UserOrder> findByUserEmail(String email) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.FIND_ORDER_BY_USER_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            List<UserOrder> orders = new ArrayList<>();

            while (resultSet.next()) {
                UserOrder order = createOrderFromSql(resultSet);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException("Error while finding orders by user email in database", e);
        }
    }

    @Override
    public double calculateDateProfit(long date) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.CALCULATE_DATE_PROFIT)) {
            statement.setLong(1, date);
            ResultSet resultSet = statement.executeQuery();
            double profit = 0;
            if (resultSet.next()) {
                profit = resultSet.getDouble(ColumnName.PROFIT);
            }
            return profit;
        } catch (SQLException e) {
            throw new DaoException("Error while finding order by carId from database", e);
        }
    }

    @Override
    public Optional<UserOrder> findByCarId(long carId) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.FIND_ORDER_BY_CAR_ID)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            Optional<UserOrder> order = Optional.empty();
            if (resultSet.next()) {
                order = Optional.of(createOrderFromSql(resultSet));
            }
            return order;
        } catch (SQLException e) {
            throw new DaoException("Error while finding order by carId from database", e);
        }
    }

    private UserOrder createOrderFromSql(ResultSet resultSet) throws SQLException {
        long orderId = Long.parseLong(resultSet.getString(ColumnName.ORDER_ID));
        LocalDate orderDate = DateConverter.convertToDate(resultSet.getLong(ColumnName.ORDER_DATE));
        boolean inProcessing = resultSet.getBoolean(ColumnName.IN_PROCESSING);
        long carId = Long.parseLong(resultSet.getString(ColumnName.CAR_ID));
        CarBrand brand = CarBrand.valueOf(resultSet.getString(ColumnName.BRAND).toUpperCase());
        double price = resultSet.getDouble(ColumnName.PRICE);
        String description = resultSet.getString(ColumnName.DESCRIPTION);
        String imageName = resultSet.getString(ColumnName.IMAGE_NAME);
        boolean isAvailable = resultSet.getBoolean(ColumnName.IS_AVAILABLE);
        LocalDate addedDate = DateConverter.convertToDate(resultSet.getLong(ColumnName.ADDED_DATE));
        String model = resultSet.getString(ColumnName.MODEL);
        int manufactureYear = Integer.parseInt(resultSet.getString(ColumnName.MANUFACTURE_YEAR));
        CarColor color = CarColor.valueOf(resultSet.getString(ColumnName.COLOR).toUpperCase());
        BoxType boxType = BoxType.valueOf(resultSet.getString(ColumnName.BOX_TYPE).toUpperCase());
        CarEngine engineType = CarEngine.valueOf(resultSet.getString(ColumnName.ENGINE_TYPE).toUpperCase());
        Car car = new Car(carId, brand, model, manufactureYear, price, description, imageName, addedDate, isAvailable,
                color, boxType, engineType);
        String login = resultSet.getString(ColumnName.LOGIN);
        boolean isAdmin = resultSet.getBoolean(ColumnName.IS_ADMIN);
        boolean isBlocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
        Account account =  new Account(login, isAdmin, isBlocked);
        String email = resultSet.getString(ColumnName.EMAIL);
        String name = resultSet.getString(ColumnName.NAME);
        String surname = resultSet.getString(ColumnName.SURNAME);
        User user = new User(account, email, name, surname);

        return new UserOrder(orderId, orderDate, user, car, inProcessing);
    }
}
