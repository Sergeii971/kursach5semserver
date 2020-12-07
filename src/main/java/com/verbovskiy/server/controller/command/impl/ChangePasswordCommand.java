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

public class ChangePasswordCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(ChangePasswordCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        UserService service = new UserServiceImpl();
        String email = (String) request.get(RequestParameter.EMAIL);
        String password = (String) request.get(RequestParameter.PASSWORD);
        String newPassword = (String) request.get(RequestParameter.NEW_PASSWORD);
        String passwordConfirmation = (String) request.get(RequestParameter.PASSWORD_CONFIRMATION);
        Map<String, Object> response = new HashMap<>();

        try {
            if (service.verifyAccount(email, password) && service.updatePassword(email, newPassword, passwordConfirmation)) {
                response.put(AttributeKey.SUCCESSFUL_PASSWORD_CHANGE, true);
            } else {
                response.put(AttributeKey.SUCCESSFUL_PASSWORD_CHANGE, false);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return response;
    }
}
