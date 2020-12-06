package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.AttributeKey;
import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.service.UserService;
import com.verbovskiy.server.model.service.impl.UserServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * The type registration command.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class RegistrationCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        UserService service = new UserServiceImpl();
        String email = (String) request.get(RequestParameter.EMAIL);
        String password = (String) request.get(RequestParameter.PASSWORD);
        boolean isAdmin = false;
        boolean isBlocked = false;
        String name = (String) request.get(RequestParameter.NAME);
        String surname = (String) request.get(RequestParameter.SURNAME);
        Map<String, Object> response = new HashMap<>();

        try {
            Map<String, Boolean> incorrectParameter = service.add(email, password, isAdmin, isBlocked,
                    email, name, surname);
            response.put(AttributeKey.INCORRECT_PARAMETER, incorrectParameter);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return response;
    }
}
