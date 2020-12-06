package com.verbovskiy.server.model.dao.impl;

import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.model.connection.ConnectionPool;
import com.verbovskiy.server.model.dao.AccountDao;
import com.verbovskiy.server.model.dao.ColumnName;
import com.verbovskiy.server.model.dao.query.DatabaseQuery;
import com.verbovskiy.server.model.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type account dao.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class AccountDaoImpl implements AccountDao {
    private static AccountDao instance;

    private AccountDaoImpl() {
    }

    /**
     * Get instance account dao.
     *
     * @return the account dao
     */
    public static AccountDao getInstance() {
        if (instance == null) {
            instance = new AccountDaoImpl();
        }
        return instance;
    }
    @Override
    public List<Account> findAll() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.FIND_ALL_ACCOUNTS)) {
            List<Account> accounts = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Account account = createAccountFromSql(resultSet);
                    accounts.add(account);
                }
            return accounts;
        } catch (SQLException e) {
            throw new DaoException("Error while get all accounts from database", e);
        }
    }

    @Override
    public void changeBlockStatus(String login, boolean status) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.CHANGE_BLOCK_STATUS)) {
            statement.setBoolean(1, status);
            statement.setString(2, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error during changing account block status in database", e);
        }
    }

    @Override
    public Optional<Account> findByLogin(String login) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.FIND_ACCOUNT_BY_LOGIN)) {
            statement.setString(1, login);
            Optional<Account> account = Optional.empty();
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                account = Optional.of(createAccountFromSql(resultSet));
            }
            return account;
        } catch (SQLException e) {
            throw new DaoException("Error while finding accont by login from database", e);
        }
    }

    @Override
    public Optional<Account> findByLoginPassword(String login, String password) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.FIND_ACCOUNT_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            Optional<Account> account = Optional.empty();

            if (resultSet.next()) {
                account = Optional.of(createAccountFromSql(resultSet));
            }
            return account;
        } catch (SQLException e) {
            throw new DaoException("Error while finding account by login and password from database", e);
        }
    }

    @Override
    public void changePassword(String login, String password) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseQuery.CHANGE_PASSWORD)) {
            statement.setString(1, password);
            statement.setString(2, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error during changing account password in database", e);
        }
    }

    private Account createAccountFromSql(ResultSet resultSet) throws SQLException {
        String login = resultSet.getString(ColumnName.LOGIN);
        boolean isAdmin = resultSet.getBoolean(ColumnName.IS_ADMIN);
        boolean isBlocked = resultSet.getBoolean(ColumnName.IS_BLOCKED);

        return new Account(login, isAdmin, isBlocked);
    }
}
