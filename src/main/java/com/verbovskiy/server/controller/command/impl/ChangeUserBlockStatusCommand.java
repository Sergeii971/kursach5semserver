package com.verbovskiy.server.controller.command.impl;

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

public class ChangeUserBlockStatusCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(ChangeUserBlockStatusCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        UserService service = new UserServiceImpl();
        boolean userBlockStatus = (boolean) request.get(RequestParameter.USER_STATUS);
        String email = (String) request.get(RequestParameter.USER_ID);

        try {
            service.updateUserBlockStatus(email, userBlockStatus);
        } catch (ServiceException e) {
            logger.log(Level.ERROR,  e);
        }
        return new HashMap<>();
    }
}
