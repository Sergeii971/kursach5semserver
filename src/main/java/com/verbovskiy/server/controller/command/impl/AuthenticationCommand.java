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
 * The type authentication command.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class AuthenticationCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(AuthenticationCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        UserService service = new UserServiceImpl();
        String email = (String) request.get(RequestParameter.LOGIN);
        String password = (String) request.get(RequestParameter.PASSWORD);
        Map<String, Object> response = new HashMap<>();

        try {
            if (service.verifyAccount(email, password)) {
                response.put(AttributeKey.SUCCESSFUL_AUTHENTICATION, true);
                   if(!service.isBlocked(email)) {
                       response.put(AttributeKey.SUCCESSFUL_ACTIVATION, true);
                       if (service.isAdmin(email)) {
                           response.put(RequestParameter.IS_ADMIN, true);
                       } else {
                           response.put(RequestParameter.IS_ADMIN, false);
                       }
                   } else {
                       response.put(AttributeKey.SUCCESSFUL_ACTIVATION, false);
                   }
            } else {
                response.put(AttributeKey.SUCCESSFUL_AUTHENTICATION, false);
                response.put(AttributeKey.SUCCESSFUL_ACTIVATION, false);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return response;
    }
}
