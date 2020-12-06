package com.verbovskiy.server.controller.command.impl;

import com.verbovskiy.server.controller.command.ActionCommand;
import com.verbovskiy.server.controller.command.RequestParameter;
import com.verbovskiy.server.exception.ServiceException;
import com.verbovskiy.server.model.entity.User;
import com.verbovskiy.server.model.service.UserService;
import com.verbovskiy.server.model.service.impl.UserServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SearchUsersCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(SearchUsersCommand.class);

    @Override
    public Map<String, Object> execute(Map<String, Object> request) {
        UserService service = new UserServiceImpl();
        String adminEmail = (String) request.get(RequestParameter.EMAIL);
        Map<String, Object> response = new HashMap<>();

        try {
            String parameter = (String) request.get(RequestParameter.SEARCH_PARAMETER);
            List<User> users = service.searchUsers(parameter);
            Optional<User> admin = service.findAdminByEmail(adminEmail);

            admin.ifPresent(users::remove);
            response.put(RequestParameter.SIZE, users.size());
            for (int i = 0; i < users.size(); i++) {
                response.put(RequestParameter.LOGIN + i, users.get(i).getEmail());
                response.put(RequestParameter.NAME + i, users.get(i).getName());
                response.put(RequestParameter.SURNAME + i, users.get(i).getSurname());
                response.put(RequestParameter.IS_BLOCKED + i, users.get(i).getAccount().isBlocked());
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return response;
    }
}
