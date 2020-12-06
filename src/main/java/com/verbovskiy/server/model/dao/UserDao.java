package com.verbovskiy.server.model.dao;

import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public interface UserDao {
    /**
     * Add user
     *
     * @param login the login
     * @param email the email
     * @param name the name
     * @param surname the surname
     * @param encryptedPassword the encrypted password
     * @param isAdmin is admin
     * @param isBlocked is blocked
     * @throws DaoException the dao exception
     */
    void add(String login, String email, String name, String surname,String encryptedPassword,
             boolean isAdmin,boolean isBlocked) throws DaoException;

    /**
     * Remove user
     *
     * @param email the email
     * @throws DaoException the dao exception
     */
    void remove(String email) throws DaoException;

    /**
     * Find all users.
     *
     * @return the list of found users
     * @throws DaoException the dao exception
     */
    List<User> findAll() throws DaoException;

    /**
     * Find all blocked users.
     *
     * @return the list of found users
     * @throws DaoException the dao exception
     */
    List<User> findBlockedStatusUsers() throws DaoException;

    /**
     * Find user by email.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByEmail(String email) throws DaoException;

    /**
     * Find all users by search parameter.
     *
     * @param searchParameter the search parameter
     * @return the list of found users
     * @throws DaoException the dao exception
     */
    List<User> searchUsers(String searchParameter) throws DaoException;
}
