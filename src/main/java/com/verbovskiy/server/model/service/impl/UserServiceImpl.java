package com.verbovskiy.server.model.service.impl;

import com.verbovskiy.server.controller.AttributeKey;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.DaoException;
import com.verbovskiy.server.exception.EncryptionException;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.comparator.UserComparator;
import com.verbovskiy.server.model.dao.AccountDao;
import com.verbovskiy.server.model.dao.UserDao;
import com.verbovskiy.server.model.dao.impl.AccountDaoImpl;
import com.verbovskiy.server.model.dao.impl.UserDaoImpl;
import com.verbovskiy.server.model.entity.Account;
import com.verbovskiy.server.model.entity.User;
import com.verbovskiy.server.model.service.UserService;
import com.verbovskiy.server.util.encryption.Cryptographer;
import com.verbovskiy.server.validator.UserValidator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type User service.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final AccountDao accountDao = AccountDaoImpl.getInstance();

    @Override
    public Map<String, Boolean> add(String login, String password, boolean isAdmin, boolean isBlocked,
                                    String email, String name, String surname) throws ServiceException {
        try {
            Map<String, Boolean> incorrectParameter = UserValidator.validateUserData(email, password, name, surname);
            if (!accountDao.findByLogin(login).isPresent()) {
                if (incorrectParameter.size() == 0) {
                    Cryptographer cryptographer = new Cryptographer();
                    String encryptedPassword = cryptographer.encrypt(password);
                    userDao.add(login, email, name, surname, encryptedPassword, isAdmin, isBlocked);
                }
            } else {
                incorrectParameter.put(AttributeKey.LOGIN_EXIST, true);
            }
            return incorrectParameter;
        } catch (EncryptionException | DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void remove(String email) throws ServiceException {
        if ((email == null) || (email.isEmpty())) {
            throw new ServiceException("incorrect user data");
        }
        try {
            Optional<User> user = userDao.findByEmail(email);
            if (!user.isPresent()) {
                throw new ServiceException("incorrect user data");
            }
            userDao.remove(user.get().getEmail());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> findAllUser() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean verifyAccount(String login, String password) throws ServiceException {
        if ((login == null) || (password == null)) {
            throw new ServiceException("incorrect user data");
        }
        try {
            boolean result = true;
            if (!UserValidator.validateEmail(login) || !UserValidator.validatePassword(password)) {
                result = false;
            } else {
                Cryptographer cryptographer = new Cryptographer();
                String encryptedPassword = cryptographer.encrypt(password);
                Optional<Account> account = accountDao.findByLoginPassword(login, encryptedPassword);

                if (!account.isPresent()) {
                    result = false;
                }
            }
            return result;
        } catch (EncryptionException | DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<Account> findByLogin(String login) throws ServiceException {
        try {
              return accountDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findAdminByEmail(String email) throws ServiceException {
        try {
            Optional<User> admin = userDao.findByEmail(email);
            if (!admin.isPresent()) {
                throw new ServiceException("error while finding admin in database");
            }
            return admin;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean isAdmin(String login) throws ServiceException {
        if ((login == null)) {
            throw new ServiceException("incorrect user data");
        }
        try {
            Optional<Account> account = accountDao.findByLogin(login);
            if (!account.isPresent()) {
                throw new ServiceException("error while find information about user");
            }
            return account.get().isAdmin();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean isBlocked(String login) throws ServiceException {
        if ((login == null)) {
            throw new ServiceException("incorrect user data");
        }
        try {
            Optional<Account> account = accountDao.findByLogin(login);
            if (!account.isPresent()) {
                throw new ServiceException("error while find information about user");
            }
            return account.get().isBlocked();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean updatePassword(String login, String password, String passwordConfirmation)
            throws ServiceException {
        boolean result = false;
        try {
            if (UserValidator.validatePasswords(password, passwordConfirmation)) {
                Cryptographer cryptographer = new Cryptographer();
                String encryptedPassword = cryptographer.encrypt(password);
                accountDao.changePassword(login, encryptedPassword);
                result = true;
            }
            return result;
        } catch (EncryptionException | DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateUserBlockStatus(String login, boolean isBlocked) throws ServiceException {
        try {
        accountDao.changeBlockStatus(login, isBlocked);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> filterUsers(String userStatus) throws ServiceException {
        try {
            if (userStatus == null || userStatus.isEmpty()) {
                throw new ServiceException("incorrect user status");
            }
            List<User> users;
            if (userStatus.equals(RequestParameter.IS_BLOCKED)) {
                users = userDao.findBlockedStatusUsers();
            } else {
                users = userDao.findAll();
            }
            return users;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> sortUsers(String sortType) throws ServiceException {
        if (sortType == null || sortType.isEmpty()) {
            throw new ServiceException("incorrect SortType");
        }
        try {
            List<User> users = userDao.findAll();

            Comparator comparator = sortType.equals(RequestParameter.DEFAULT) ? null :
                    UserComparator.valueOf(sortType.toUpperCase()).getComparator();
            if (comparator != null) {
                users.sort(comparator);
            }
            return users;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> searchUsers(String parameter) throws ServiceException {
        if (parameter == null) {
            throw new ServiceException("incorrect data");
        }
        try {
            return userDao.searchUsers(parameter);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
