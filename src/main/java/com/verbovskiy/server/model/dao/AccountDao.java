package com.verbovskiy.server.model.dao;

import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.model.entity.Account;

import java.util.List;
import java.util.Optional;

/**
 * The interface Account dao.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public interface AccountDao {
    /**
     * Find all accounts.
     *
     * @return the list of found accounts
     * @throws DaoException the dao exception
     */
    List<Account> findAll() throws DaoException;

    /**
     * Change user block status.
     *
     * @throws DaoException the dao exception
     */
    void changeBlockStatus(String login, boolean status) throws DaoException;

    /**
     * Find account by login.
     *
     * @return  Optional
     * @throws DaoException the dao exception
     */
    Optional<Account> findByLogin(String login) throws DaoException;

    /**
     * Find account by login and password.
     *
     * @return optional
     * @throws DaoException the dao exception
     */
    Optional<Account> findByLoginPassword(String login, String password) throws DaoException;

    /**
     * Change user password.
     *
     * @throws DaoException the dao exception
     */
    void changePassword(String login, String password) throws DaoException;

    /**
     * Change user confirmation status.
     *
     * @throws DaoException the dao exception
     */
}
