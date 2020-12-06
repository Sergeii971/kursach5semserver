package com.verbovskiy.server.model.dao.impl;

import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.model.connection.ConnectionPool;
import com.verbovskiy.server.model.dao.ColumnName;
import com.verbovskiy.server.model.dao.UserDao;
import com.verbovskiy.server.model.dao.query.DatabaseQuery;
import com.verbovskiy.server.model.entity.Account;
import com.verbovskiy.server.model.entity.User;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type User dao.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class UserDaoImpl implements UserDao {
    private final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static UserDao instance;

    private UserDaoImpl() {
    }

    /**
     * Get instance user dao.
     *
     * @return the user dao
     */
    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public void add(String login, String email, String name, String surname, String encryptedPassword,
                    boolean isAdmin, boolean isBlocked) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();

        try {
            connection.setAutoCommit(false);
            try (PreparedStatement accountStatement = connection.prepareStatement(DatabaseQuery.ADD_ACCOUNT);
                 PreparedStatement userStatement = connection.prepareStatement(DatabaseQuery.ADD_USER)) {
                accountStatement.setString(1, login);
                accountStatement.setString(2, encryptedPassword);
                accountStatement.setBoolean(3, isAdmin);
                accountStatement.setBoolean(4, isBlocked);
                accountStatement.executeUpdate();
                userStatement.setString(1, email);
                userStatement.setString(2, name);
                userStatement.setString(3, login);
                userStatement.setString(4, surname);
                userStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException(e);
            }
        } catch (SQLException e) {
            throw new DaoException("Error while adding user to database", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "connection error", e);
            }
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void remove(String email) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();

        try {
            connection.setAutoCommit(false);
        try (PreparedStatement accountStatement = connection.prepareStatement(DatabaseQuery.REMOVE_ACCOUNT);
             PreparedStatement userStatement = connection.prepareStatement(DatabaseQuery.REMOVE_USER)) {
            userStatement.setString(1, email);
            userStatement.executeUpdate();
            accountStatement.setString(1, email);
            accountStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException(e);
        }
        } catch (SQLException e) {
            throw new DaoException("Error while removing user from database", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwable) {
                throw new DaoException("Error while removing user from database", throwable);
            }
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.FIND_ALL_USERS)) {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = createUserFromSql(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException("Error while get all users from database", e);
        }
    }

    @Override
    public List<User> findBlockedStatusUsers() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.FIND_ALL_BLOCKED_USERS)) {
            boolean isBlocked = true;
            statement.setBoolean(1, isBlocked);
            List<User> users = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = createUserFromSql(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException("Error while finding users by blocked status in database", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            Optional<User> user =Optional.empty();
            if (resultSet.next()) {
                user = Optional.of(createUserFromSql(resultSet));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException("Error while finding user by email in database", e);
        }
    }

    @Override
    public List<User> searchUsers(String searchParameter) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.SEARCH_USER)) {
            List<User> users = new ArrayList<>();
            statement.setString(1, searchParameter);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = createUserFromSql(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException("Error while search users by parameter in database", e);
        }
    }

    private User createUserFromSql(ResultSet resultSet) throws SQLException {
        String login = resultSet.getString(ColumnName.LOGIN);
        boolean isAdmin = resultSet.getBoolean(ColumnName.IS_ADMIN);
        boolean isBlocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);
        String email = resultSet.getString(ColumnName.EMAIL);
        String name = resultSet.getString(ColumnName.NAME);
        String surname = resultSet.getString(ColumnName.SURNAME);
        Account account = new Account(login, isAdmin, isBlocked);

        return new User(account, email, name, surname);
    }
}
