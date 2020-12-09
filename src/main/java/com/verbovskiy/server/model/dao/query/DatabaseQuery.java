package com.verbovskiy.server.model.dao.query;

/**
 * The type Database query.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class DatabaseQuery {
    private DatabaseQuery() {
    }

    public static final String ADD_ACCOUNT = "INSERT INTO account(login, password, isAdmin, isBlocked) " +
            "VALUES (?, ?, ?, ?)";
    public static final String REMOVE_ACCOUNT = "DELETE FROM account " + "WHERE login = ?";
    public static final String FIND_ALL_ACCOUNTS = "SELECT login, isAdmin, isBlocked" + " FROM account";
    public static final String FIND_ACCOUNT_BY_LOGIN = FIND_ALL_ACCOUNTS + " WHERE login = ? ";
    public static final String FIND_ACCOUNT_BY_LOGIN_AND_PASSWORD = FIND_ALL_ACCOUNTS + " WHERE login = ?" +
            " AND password = ?";
    public static final String CHANGE_PASSWORD = "UPDATE account SET password = ? WHERE login = ?";
    public static final String CHANGE_BLOCK_STATUS = "UPDATE account SET isBlocked = ? WHERE login = ?";

    public static final String ADD_USER = "INSERT INTO user(email, name, user_login, surname) " +
            "VALUES (?, ?, ?, ?)";
    public static final String REMOVE_USER = "DELETE FROM user " + "WHERE email = ?";
    public static final String FIND_ALL_USERS = "SELECT login, isAdmin, isBlocked, email, name, surname" +
            " FROM user Inner Join account ON account.login = user_login";
    public static final String FIND_ALL_BLOCKED_USERS = "SELECT login, isAdmin, isBlocked, email, name, surname" +
            " FROM user Inner Join account ON account.login = user_login WHERE isBlocked = ?";

    public static final String FIND_USER_BY_EMAIL = FIND_ALL_USERS + " WHERE email = ?";
    public static final String SEARCH_USER = FIND_ALL_USERS + " WHERE CONCAT(email, name, surname) LIKE concat('%' , ?, '%')";

    public static final String ADD_IMAGE_NAME = "INSERT INTO image(imageName) VALUES (?)";
    public static final String REMOVE_IMAGE = "DELETE FROM image " + "WHERE imageName = ?";

    public static final String ADD_CAR = "INSERT INTO car(image_imageName, brand, price, description, " +
            " isAvailable, addedDate, model, manufactureYear, color, engineType, boxType) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_ALL_CARS = "SELECT carId, brand, price, description, imageName, " +
            " isAvailable, addedDate, model, manufactureYear,  color, engineType, boxType" + " FROM car INNER JOIN image ON image.imageName = image_imageName";
    public static final String REMOVE_CAR = "DELETE FROM car " + "WHERE carId = ?";
    public static final String FIND_IMAGE_NAME_BY_ID = "SELECT imageName FROM car INNER JOIN image ON image.imageName = image_imageName" +
            " WHERE carId = ?";
    public static final String CHANGE_IS_AVAILABLE_CAR_STATUS = "UPDATE car SET isAvailable = ? WHERE carId = ?";
    public static final String ADMIN_FIND_CARS_BY_SEARCH_PARAMETERS = FIND_ALL_CARS + " WHERE brand LIKE concat(?, '%')" +
            " AND price >= ? AND price <= ? " + " AND color LIKE concat(?, '%') AND engineType LIKE concat(?, '%')" +
            " AND boxType LIKE concat(?, '%') AND CONCAT(brand, price, description, " +
            " model, manufactureYear,  color, engineType, boxType)" + " LIKE concat('%' , ?, '%') ORDER BY addedDate DESC";
    public static final String USER_FIND_CARS_BY_SEARCH_PARAMETERS = FIND_ALL_CARS + " WHERE brand LIKE concat(?, '%')" +
            " AND price >= ? AND price <= ? " + " AND isAvailable = ? AND color LIKE concat(?, '%')" +
            " AND engineType LIKE concat(?, '%') AND boxType LIKE concat(?, '%') AND CONCAT(brand, price, description, " +
            " model, manufactureYear, color, engineType, boxType)" + " LIKE concat('%' , ?, '%') ORDER BY addedDate DESC";
    public static final String FIND_ALL_AVAILABLE_CAR = FIND_ALL_CARS  + " WHERE isAvailable = ?";
    public static final String FIND_CAR_BY_ID = FIND_ALL_CARS + " WHERE carId = ? ";

    public static final String ADD_ORDER = "INSERT INTO `order` (`orderDate`, `user_email`, `car_carId`, inProcessing) VALUES (?, ?, ?, ?) ";
    public static final String FIND_ALL_ORDERS = "SELECT orderId, orderDate, inProcessing, carId, brand, price, description," +
            " imageName, isAvailable, addedDate, model, manufactureYear, color, engineType, boxType," +
            " email, name, surname, login, isAdmin, isBlocked" +
            " FROM `order` Inner Join car ON carId = car_carId INNER JOIN image ON image.imageName = image_imageName Inner Join user" +
            " ON user.email = user_email  Inner Join account ON account.login = user_login";
    public static final String FIND_ORDER_BY_CAR_ID = FIND_ALL_ORDERS + " WHERE carId = ?";
    public static final String REMOVE_ORDER = "DELETE FROM `order` " + "WHERE orderId = ?";
    public static final String FIND_ORDERS_BY_SEARCH_PARAMETERS = FIND_ALL_ORDERS + " WHERE brand LIKE concat(?, '%')" +
            " AND color LIKE concat(?, '%') AND engineType LIKE concat(?, '%') AND boxType LIKE concat(?, '%')" +
            " AND CONCAT(brand, price, description, model, manufactureYear, color, engineType, boxType,  email, name," +
            " surname) LIKE concat('%' , ?, '%') ORDER BY orderDate DESC";
    public static final String CHANGE_IS_PROCESSING_ORDER_STATUS = "UPDATE `order` SET inProcessing = ? WHERE orderId = ?";
    public static final String FIND_ORDER_BY_USER_EMAIL = FIND_ALL_ORDERS + " WHERE email = ? ORDER BY date DESC";
}